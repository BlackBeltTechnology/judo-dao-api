package hu.blackbelt.judo.dao.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
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
    public void testEquls() {
        Payload payload = Payload.map("k1", null, "k2", "string");
        assertThat(Payload.map("k2", "string", "k1", null), equalTo(Payload.map("k1", null, "k2", "string")));
        assertThat(Payload.map("k2", "string", "k1", null, "__$created", true), equalTo(Payload.map("k1", null, "k2", "string")));

    }

    @Test
    public void testEntries() {
        // empty
        Payload emptyPayload = Payload.map();
        assertEquals(0, emptyPayload.size());

        // 1 item
        Object object = new Object();
        Payload singlePayload = Payload.map(Payload.entry("v1", object));
        assertEquals(1, singlePayload.size());
        assertEquals(object, singlePayload.getAs(Object.class, "v1"));

        // more than 1 items
        Object object2 = new Object();
        Object object3 = new Object();
        Payload multiPayload = Payload.map(
                new SimpleEntry<>("v1", object),
                new SimpleEntry<>("v2", object2),
                new SimpleEntry<>("v3", object3)
        );
        assertEquals(3, multiPayload.size());
        assertEquals(object, multiPayload.getAs(Object.class, "v1"));
        assertEquals(object2, multiPayload.getAs(Object.class, "v2"));
        assertEquals(object3, multiPayload.getAs(Object.class, "v3"));
    }

    @Test
    public void testEntryStress() {
        // 100 key-value pairs
        Payload payload = Payload.map(
                Payload.entry("v1", new Object()), Payload.entry("v2", new Object()), Payload.entry("v3", new Object()),
                Payload.entry("v4", new Object()), Payload.entry("v5", new Object()), Payload.entry("v6", new Object()),
                Payload.entry("v7", new Object()), Payload.entry("v8", new Object()), Payload.entry("v9", new Object()),
                Payload.entry("v10", new Object()), Payload.entry("v11", new Object()), Payload.entry("v12", new Object()),
                Payload.entry("v13", new Object()), Payload.entry("v14", new Object()), Payload.entry("v15", new Object()),
                Payload.entry("v16", new Object()), Payload.entry("v17", new Object()), Payload.entry("v18", new Object()),
                Payload.entry("v19", new Object()), Payload.entry("v20", new Object()), Payload.entry("v21", new Object()),
                Payload.entry("v22", new Object()), Payload.entry("v23", new Object()), Payload.entry("v24", new Object()),
                Payload.entry("v25", new Object()), Payload.entry("v26", new Object()), Payload.entry("v27", new Object()),
                Payload.entry("v28", new Object()), Payload.entry("v29", new Object()), Payload.entry("v30", new Object()),
                Payload.entry("v31", new Object()), Payload.entry("v32", new Object()), Payload.entry("v33", new Object()),
                Payload.entry("v34", new Object()), Payload.entry("v35", new Object()), Payload.entry("v36", new Object()),
                Payload.entry("v37", new Object()), Payload.entry("v38", new Object()), Payload.entry("v39", new Object()),
                Payload.entry("v40", new Object()), Payload.entry("v41", new Object()), Payload.entry("v42", new Object()),
                Payload.entry("v43", new Object()), Payload.entry("v44", new Object()), Payload.entry("v45", new Object()),
                Payload.entry("v46", new Object()), Payload.entry("v47", new Object()), Payload.entry("v48", new Object()),
                Payload.entry("v49", new Object()), Payload.entry("v50", new Object()), Payload.entry("v51", new Object()),
                Payload.entry("v52", new Object()), Payload.entry("v53", new Object()), Payload.entry("v54", new Object()),
                Payload.entry("v55", new Object()), Payload.entry("v56", new Object()), Payload.entry("v57", new Object()),
                Payload.entry("v58", new Object()), Payload.entry("v59", new Object()), Payload.entry("v60", new Object()),
                Payload.entry("v61", new Object()), Payload.entry("v62", new Object()), Payload.entry("v63", new Object()),
                Payload.entry("v64", new Object()), Payload.entry("v65", new Object()), Payload.entry("v66", new Object()),
                Payload.entry("v67", new Object()), Payload.entry("v68", new Object()), Payload.entry("v69", new Object()),
                Payload.entry("v70", new Object()), Payload.entry("v71", new Object()), Payload.entry("v72", new Object()),
                Payload.entry("v73", new Object()), Payload.entry("v74", new Object()), Payload.entry("v75", new Object()),
                Payload.entry("v76", new Object()), Payload.entry("v77", new Object()), Payload.entry("v78", new Object()),
                Payload.entry("v79", new Object()), Payload.entry("v80", new Object()), Payload.entry("v81", new Object()),
                Payload.entry("v82", new Object()), Payload.entry("v83", new Object()), Payload.entry("v84", new Object()),
                Payload.entry("v85", new Object()), Payload.entry("v86", new Object()), Payload.entry("v87", new Object()),
                Payload.entry("v88", new Object()), Payload.entry("v89", new Object()), Payload.entry("v90", new Object()),
                Payload.entry("v91", new Object()), Payload.entry("v92", new Object()), Payload.entry("v93", new Object()),
                Payload.entry("v94", new Object()), Payload.entry("v95", new Object()), Payload.entry("v96", new Object()),
                Payload.entry("v97", new Object()), Payload.entry("v98", new Object()), Payload.entry("v99", new Object()),
                Payload.entry("v100", new Object())
        );
    }
}
