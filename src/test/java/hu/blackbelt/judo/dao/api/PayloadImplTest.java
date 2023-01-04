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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PayloadImplTest {

    @Test
    public void testPayloadFromMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("k1", "string");
        map.put("null", null);
        map.put("map", ImmutableMap.of("map2", "map2v", "map1", "map1v"));
        map.put("collection", ImmutableSet.of(
                        ImmutableMap.of("subcol2_2", "subcolval2_2", "subcol2_1", "subval2_2"),
                        ImmutableMap.of("subcol1_2", "subcolval1_2", "subcol1_1", "subval1_2")
                ));
        map.put("list", ImmutableList.of(
                        ImmutableMap.of("sublst2_2", "sublstval2_2", "sublst2_1", "sublst2_2"),
                        ImmutableMap.of("sublst1_2", "sublstval1_2", "sublst1_1", "sublst1_2")
                ));

        Payload payload = Payload.asPayload(map);
        assertThat(payload, equalTo(map));
        assertThat(payload, isA(Payload.class));
        assertThat(payload.getAsPayload("map"), isA(Payload.class));
        assertThat(payload.getAsCollectionPayload("collection"), isA(Collection.class));
        assertThat((Set) payload.getAsCollectionPayload("collection"), isA(Set.class));
        assertThat((List) payload.getAsCollectionPayload("list"), isA(List.class));

    }

    @Test
    public void testPayloadStatic() {
        Payload payload = Payload.map("k1", null, "k2", "string");
        assertThat(payload, isA(Payload.class));
        assertThat(payload.getAs(String.class, "k1"), equalTo(null));
        assertThat(payload.getAs(String.class, "k2"), equalTo("string"));
    }

    @Test
    public void testEquals() {
        Payload payload = Payload.map("k1", null, "k2", "string");
        assertThat(Payload.map("k2", "string", "k1", null), equalTo(Payload.map("k1", null, "k2", "string")));
        assertThat(Payload.map("k2", "string", "k1", null, "__$created", true), equalTo(Payload.map("k1", null, "k2", "string")));

    }

    @Test
    public void testEntries() {
        // empty
        Payload emptyPayload = Payload.map();
        assertEquals(0, emptyPayload.size());

        // null
        Payload nullPayload = Payload.map(null);
        assertNull(nullPayload);

        // null value
        Payload nullValuePayload = Payload.map(Payload.entry("k1", null), Payload.entry("k2", null));
        assertEquals(2, nullValuePayload.size());
        assertNull(nullValuePayload.getAs(Object.class, "k1"));
        assertNull(nullValuePayload.getAs(Object.class, "k2"));

        // null key
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                             () -> Payload.map(Payload.entry(null, "v1"),
                                               Payload.entry(null, "v2")));
        assertEquals("Payload contains null key(s)", exception.getMessage());

        // 1 item
        Object object = new Object();
        Payload singlePayload = Payload.map(Payload.entry("v1", object));
        assertEquals(1, singlePayload.size());
        assertEquals(object, singlePayload.getAs(Object.class, "v1"));

        // more than 1 items
        Object object2 = new Object();
        Object object3 = new Object();
        Payload multiPayload = Payload.map(
                Payload.entry("v1", object),
                Payload.entry("v2", object2),
                Payload.entry("v3", object3)
        );
        assertEquals(3, multiPayload.size());
        assertEquals(object, multiPayload.getAs(Object.class, "v1"));
        assertEquals(object2, multiPayload.getAs(Object.class, "v2"));
        assertEquals(object3, multiPayload.getAs(Object.class, "v3"));
    }


    @Test
    public void testToJson() {
        Map<String, Object> map = new HashMap<>();

        map.put("k1", "string");
        map.put("null", null);
        map.put("map", ImmutableMap.of("map2", "map2v", "map1", "map1v"));
        map.put("collection", ImmutableSet.of(
                ImmutableMap.of("subcol2_2", "subcolval2_2", "subcol2_1", "subval2_2"),
                ImmutableMap.of("subcol1_2", "subcolval1_2", "subcol1_1", "subval1_2")
        ));
        map.put("list", ImmutableList.of(
                ImmutableMap.of("sublst2_2", "sublstval2_2", "sublst2_1", "sublst2_2"),
                ImmutableMap.of("sublst1_2", "sublstval1_2", "sublst1_1", "sublst1_2")
        ));

        Payload payload = Payload.asPayload(map);

        assertEquals("{\n" +
                "  \"collection\" : [ {\n" +
                "    \"subcol2_1\" : \"subval2_2\",\n" +
                "    \"subcol2_2\" : \"subcolval2_2\"\n" +
                "  }, {\n" +
                "    \"subcol1_1\" : \"subval1_2\",\n" +
                "    \"subcol1_2\" : \"subcolval1_2\"\n" +
                "  } ],\n" +
                "  \"k1\" : \"string\",\n" +
                "  \"list\" : [ {\n" +
                "    \"sublst2_1\" : \"sublst2_2\",\n" +
                "    \"sublst2_2\" : \"sublstval2_2\"\n" +
                "  }, {\n" +
                "    \"sublst1_1\" : \"sublst1_2\",\n" +
                "    \"sublst1_2\" : \"sublstval1_2\"\n" +
                "  } ],\n" +
                "  \"map\" : {\n" +
                "    \"map1\" : \"map1v\",\n" +
                "    \"map2\" : \"map2v\"\n" +
                "  },\n" +
                "  \"null\" : null\n" +
                "}",  payload.toString());

    }

}
