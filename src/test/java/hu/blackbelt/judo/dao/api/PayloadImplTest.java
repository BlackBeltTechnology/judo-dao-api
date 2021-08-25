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
        Payload singlePayload = Payload.map(new SimpleEntry<>("v1", object));
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
                new SimpleEntry<>("v1", new Object()), new SimpleEntry<>("v2", new Object()),
                new SimpleEntry<>("v3", new Object()), new SimpleEntry<>("v4", new Object()),
                new SimpleEntry<>("v5", new Object()), new SimpleEntry<>("v6", new Object()),
                new SimpleEntry<>("v7", new Object()), new SimpleEntry<>("v8", new Object()),
                new SimpleEntry<>("v9", new Object()), new SimpleEntry<>("v10", new Object()),
                new SimpleEntry<>("v11", new Object()), new SimpleEntry<>("v12", new Object()),
                new SimpleEntry<>("v13", new Object()), new SimpleEntry<>("v14", new Object()),
                new SimpleEntry<>("v15", new Object()), new SimpleEntry<>("v16", new Object()),
                new SimpleEntry<>("v17", new Object()), new SimpleEntry<>("v18", new Object()),
                new SimpleEntry<>("v19", new Object()), new SimpleEntry<>("v20", new Object()),
                new SimpleEntry<>("v21", new Object()), new SimpleEntry<>("v22", new Object()),
                new SimpleEntry<>("v23", new Object()), new SimpleEntry<>("v24", new Object()),
                new SimpleEntry<>("v25", new Object()), new SimpleEntry<>("v26", new Object()),
                new SimpleEntry<>("v27", new Object()), new SimpleEntry<>("v28", new Object()),
                new SimpleEntry<>("v29", new Object()), new SimpleEntry<>("v30", new Object()),
                new SimpleEntry<>("v31", new Object()), new SimpleEntry<>("v32", new Object()),
                new SimpleEntry<>("v33", new Object()), new SimpleEntry<>("v34", new Object()),
                new SimpleEntry<>("v35", new Object()), new SimpleEntry<>("v36", new Object()),
                new SimpleEntry<>("v37", new Object()), new SimpleEntry<>("v38", new Object()),
                new SimpleEntry<>("v39", new Object()), new SimpleEntry<>("v40", new Object()),
                new SimpleEntry<>("v41", new Object()), new SimpleEntry<>("v42", new Object()),
                new SimpleEntry<>("v43", new Object()), new SimpleEntry<>("v44", new Object()),
                new SimpleEntry<>("v45", new Object()), new SimpleEntry<>("v46", new Object()),
                new SimpleEntry<>("v47", new Object()), new SimpleEntry<>("v48", new Object()),
                new SimpleEntry<>("v49", new Object()), new SimpleEntry<>("v50", new Object()),
                new SimpleEntry<>("v51", new Object()), new SimpleEntry<>("v52", new Object()),
                new SimpleEntry<>("v53", new Object()), new SimpleEntry<>("v54", new Object()),
                new SimpleEntry<>("v55", new Object()), new SimpleEntry<>("v56", new Object()),
                new SimpleEntry<>("v57", new Object()), new SimpleEntry<>("v58", new Object()),
                new SimpleEntry<>("v59", new Object()), new SimpleEntry<>("v60", new Object()),
                new SimpleEntry<>("v61", new Object()), new SimpleEntry<>("v62", new Object()),
                new SimpleEntry<>("v63", new Object()), new SimpleEntry<>("v64", new Object()),
                new SimpleEntry<>("v65", new Object()), new SimpleEntry<>("v66", new Object()),
                new SimpleEntry<>("v67", new Object()), new SimpleEntry<>("v68", new Object()),
                new SimpleEntry<>("v69", new Object()), new SimpleEntry<>("v70", new Object()),
                new SimpleEntry<>("v71", new Object()), new SimpleEntry<>("v72", new Object()),
                new SimpleEntry<>("v73", new Object()), new SimpleEntry<>("v74", new Object()),
                new SimpleEntry<>("v75", new Object()), new SimpleEntry<>("v76", new Object()),
                new SimpleEntry<>("v77", new Object()), new SimpleEntry<>("v78", new Object()),
                new SimpleEntry<>("v79", new Object()), new SimpleEntry<>("v80", new Object()),
                new SimpleEntry<>("v81", new Object()), new SimpleEntry<>("v82", new Object()),
                new SimpleEntry<>("v83", new Object()), new SimpleEntry<>("v84", new Object()),
                new SimpleEntry<>("v85", new Object()), new SimpleEntry<>("v86", new Object()),
                new SimpleEntry<>("v87", new Object()), new SimpleEntry<>("v88", new Object()),
                new SimpleEntry<>("v89", new Object()), new SimpleEntry<>("v90", new Object()),
                new SimpleEntry<>("v91", new Object()), new SimpleEntry<>("v92", new Object()),
                new SimpleEntry<>("v93", new Object()), new SimpleEntry<>("v94", new Object()),
                new SimpleEntry<>("v95", new Object()), new SimpleEntry<>("v96", new Object()),
                new SimpleEntry<>("v97", new Object()), new SimpleEntry<>("v98", new Object()),
                new SimpleEntry<>("v99", new Object()), new SimpleEntry<>("v100", new Object())
        );
    }
}
