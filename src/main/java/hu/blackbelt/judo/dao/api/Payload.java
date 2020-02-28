package hu.blackbelt.judo.dao.api;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface Payload extends Map<String, Object> {

    static Payload asPayload(Map<String, Object> objectMap) {
        return new PayloadImpl(objectMap);
    }

    static Payload empty() {
        return new PayloadImpl(ImmutableMap.of());
    }

    static Payload map(String k1, Object v1) {
        return asPayload(ImmutableBiMap.of(k1, v1));
    }

    static Payload map(String k1, Object v1, String k2, Object v2) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); }});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5) {

        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15, String k16, Object v16) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15); put(k16, v16);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15, String k16, Object v16,
                       String k17, Object v17) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15); put(k16, v16); put(k17, v17);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15, String k16, Object v16,
                       String k17, Object v17, String k18, Object v18) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15); put(k16, v16); put(k17, v17); put(k18, v18);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15, String k16, Object v16,
                       String k17, Object v17, String k18, Object v18, String k19, Object v19) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15); put(k16, v16); put(k17, v17); put(k18, v18); put(k19, v19);}});
    }

    static Payload map(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4,
                       String k5, Object v5, String k6, Object v6, String k7, Object v7, String k8, Object v8,
                       String k9, Object v9, String k10, Object v10, String k11, Object v11, String k12, Object v12,
                       String k13, Object v13, String k14, Object v14, String k15, Object v15, String k16, Object v16,
                       String k17, Object v17, String k18, Object v18, String k19, Object v19, String k20, Object v20) {
        return asPayload(new HashMap<String, Object>() {{ put(k1, v1); put(k2, v2); put(k3, v3); put(k4, v4);
            put(k5, v5); put(k6, v6); put(k7, v7); put(k8, v8); put(k9, v9); put(k10, v10); put(k11, v11); put(k12, v12);
            put(k13, v13); put(k14, v14); put(k15, v15); put(k16, v16); put(k17, v17); put(k18, v18); put(k19, v19);
            put(k20, v20);}});
    }

    Payload getAsPayload(String name);

    Collection<Payload> getAsCollectionPayload(String name);

    <T> T getAs(Class<T> type, String name);
}
