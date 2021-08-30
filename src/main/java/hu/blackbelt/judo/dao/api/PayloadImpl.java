package hu.blackbelt.judo.dao.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static hu.blackbelt.judo.dao.api.Payload.asPayload;

public class PayloadImpl implements Payload {

    public static final Gson GSON = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                @Override
                public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public ZonedDateTime read(JsonReader in) throws IOException {
                    return ZonedDateTime.parse(in.nextString());
                }
            })
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();
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
        return GSON.toJson(internal);
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

