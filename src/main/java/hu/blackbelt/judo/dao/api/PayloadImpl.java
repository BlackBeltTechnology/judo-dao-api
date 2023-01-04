package hu.blackbelt.judo.dao.api;

/*-
 * #%L
 * Judo DAO API
 * %%
 * Copyright (C) 2018 - 2022 BlackBelt Technology
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static hu.blackbelt.judo.dao.api.Payload.asPayload;

public class PayloadImpl implements Payload {

    public static final String TRANSIENT_PREFIX = "__$";

    Map<String, Object> internal;

    public PayloadImpl(Map<String, Object> map) {
        for (String key : map.keySet()) {
            if (key == null) {
                throw new IllegalArgumentException("Payload contains null key(s)");
            }
        }
        this.internal = new TreeMap<>();
        for (String key : new TreeSet<>(map.keySet())) {
            Object value = map.get(key);
            if (value instanceof List) {
                this.internal.put(key, ((List<Map<String, Object>>) value).stream().map(
                        e -> asPayload(e)).collect(Collectors.toList()));
            } else if (value instanceof Collection) {
                this.internal.put(key, ((Collection<Map<String, Object>>) value).stream().map(
                        e -> asPayload(e)).collect(Collectors.toSet()));
            } else if (value instanceof Map) {
                this.internal.put(key, asPayload((Map<String, Object>) value));
            } else {
                this.internal.put(key, value);
            }
        }
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return internal.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internal.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return internal.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return internal.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return internal.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        internal.putAll(m);
    }

    @Override
    public void clear() {
        internal.clear();
    }

    @Override
    public Set<String> keySet() {
        return internal.keySet();
    }

    @Override
    public Collection<Object> values() {
        return internal.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return internal.entrySet();
    }

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullKeySerializer(new JacksonNullKeySerializer());
        String jsonResult = null;
        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(internal);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonResult;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map right = new TreeMap((Map) obj);
        Map left = new TreeMap(internal);

        // Remove hidden fields
        ((Set) right.keySet().stream()
                .filter(k -> k.toString().startsWith(TRANSIENT_PREFIX))
                .collect(Collectors.toSet()))
                .forEach(k -> right.remove(k));

        ((Set) left.keySet().stream()
                .filter(k -> k.toString().startsWith(TRANSIENT_PREFIX))
                .collect(Collectors.toSet()))
                .forEach(k -> left.remove(k));

        return left.equals(right);
    }

    @Override
    public int hashCode() {
        return internal.hashCode();
    }

    @Override
    public Payload getAsPayload(String name) {
        if (!containsKey(name) || get(name) == null) {
            return null;
        } else if (get(name) instanceof Payload) {
            return (Payload) get(name);
        } else {
            throw new IllegalArgumentException("The payload element in the given key: " + name + " is not a Payload. ");
        }
    }

    @Override
    public Collection<Payload> getAsCollectionPayload(String name) {
        if (!containsKey(name) || get(name) == null) {
            return null;
        } else if (get(name) instanceof Collection) {
            return (Collection<Payload>) get(name);
        } else {
            throw new IllegalArgumentException("The payload element in the given key: " + name + " is not a Collection. ");
        }
    }

    @Override
    public <T> T getAs(Class<T> type, String name) {
        if (!containsKey(name) || get(name) == null) {
            return null;
        } else if (type.isAssignableFrom(get(name).getClass())) {
            return (T) get(name);
        } else {
            throw new IllegalArgumentException("The payload element in the given key: " + name + " is not a " + type.getName() +  ". ");
        }
    }
}

