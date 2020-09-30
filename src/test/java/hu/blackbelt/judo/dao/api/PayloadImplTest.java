package hu.blackbelt.judo.dao.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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


}