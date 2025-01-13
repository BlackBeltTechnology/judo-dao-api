package hu.blackbelt.judo.dao.api;

/*-
 * #%L
 * JUDO Runtime Core :: Parent
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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;

public class PayloadTraverser {

    @NonNull
    private BiConsumer<Payload, PayloadTraverserContext> processor;

    @NonNull
    private Predicate<EReference> predicate;

    private static final Predicate<EStructuralFeature> IS_COLLECTION = ETypedElement::isMany;

    PayloadTraverser(@NonNull BiConsumer<Payload, PayloadTraverserContext> processor, @NonNull Predicate<EReference> predicate) {
        this.processor = processor;
        this.predicate = predicate;
    }

    public static PayloadTraverserBuilder builder() {
        return new PayloadTraverserBuilder();
    }

    public Payload traverse(final Payload payload, final EClass transferObjectType) {
        return traverse(payload, PayloadTraverserContext
                .builder()
                .type(transferObjectType)
                .path(Collections.emptyList())
                .build());
    }

    private Payload traverse(final Payload payload, final PayloadTraverserContext ctx) {
        if (payload == null) {
            return null;
        }

        processor.accept(payload, ctx);

        ctx.getType().getEAllReferences().stream()
           .filter(predicate)
           .filter(r -> payload.containsKey(r.getName()) && payload.get(r.getName()) != null)
           .collect(toReferencePayloadMapOfPayloadCollection(payload))
           .forEach((key, value) -> {
               int idx = 0;
               for (Iterator<Payload> it = value.iterator(); it.hasNext(); idx++) {
                   final Payload p = it.next();
                   traverse(p, PayloadTraverserContext.builder()
                                                      .type(key.getEReferenceType())
                                                      .path(ImmutableList.<PathEntry>builder()
                                                                         .addAll(ctx.getPath())
                                                                         .add(PathEntry.builder()
                                                                                       .reference(key)
                                                                                       .index(idx)
                                                                                       .build())
                                                                         .build())
                                                      .build());
               }
           });

        return payload;
    }

    private static Collector<EReference, ?, Map<EReference, Collection<Payload>>> toReferencePayloadMapOfPayloadCollection(Payload payload) {
        return Collectors.toMap(Function.identity(), (r) -> {
            if (IS_COLLECTION.test(r)) {
                return payload.getAsCollectionPayload(r.getName());
            } else {
                return ImmutableList.of(payload.getAsPayload(r.getName()));
            }
        });
    }

    public @NonNull BiConsumer<Payload, PayloadTraverserContext> getProcessor() {
        return this.processor;
    }

    public @NonNull Predicate<EReference> getPredicate() {
        return this.predicate;
    }

    public static class PayloadTraverserContext {

        @NonNull
        private EClass type;

        @NonNull
        private List<PathEntry> path;

        PayloadTraverserContext(@NonNull EClass type, @NonNull List<PathEntry> path) {
            this.type = type;
            this.path = path;
        }

        public static PayloadTraverserContextBuilder builder() {
            return new PayloadTraverserContextBuilder();
        }

        public String getPathAsString() {
            return path.stream().map(e -> e.getReference().getName() + (e.getReference().isMany() ? "[" + e.getIndex() + "]" : "")).collect(Collectors.joining("."));
        }

        public @NonNull EClass getType() {
            return this.type;
        }

        public @NonNull List<PathEntry> getPath() {
            return this.path;
        }

        public static class PayloadTraverserContextBuilder {

            private @NonNull EClass type;
            private @NonNull List<PathEntry> path;

            PayloadTraverserContextBuilder() {
            }

            public PayloadTraverserContextBuilder type(@NonNull EClass type) {
                this.type = type;
                return this;
            }

            public PayloadTraverserContextBuilder path(@NonNull List<PathEntry> path) {
                this.path = path;
                return this;
            }

            public PayloadTraverserContext build() {
                return new PayloadTraverserContext(this.type, this.path);
            }

            public String toString() {
                return "PayloadTraverser.PayloadTraverserContext.PayloadTraverserContextBuilder(type=" + this.type + ", path=" + this.path + ")";
            }

        }

    }

    public static class PathEntry {

        @NonNull
        private final EReference reference;

        private final Integer index;

        PathEntry(@NonNull EReference reference, Integer index) {
            this.reference = reference;
            this.index = index;
        }

        public static PathEntryBuilder builder() {
            return new PathEntryBuilder();
        }

        public @NonNull EReference getReference() {
            return this.reference;
        }

        public Integer getIndex() {
            return this.index;
        }

        public static class PathEntryBuilder {

            private @NonNull EReference reference;
            private Integer index;

            PathEntryBuilder() {
            }

            public PathEntryBuilder reference(@NonNull EReference reference) {
                this.reference = reference;
                return this;
            }

            public PathEntryBuilder index(Integer index) {
                this.index = index;
                return this;
            }

            public PathEntry build() {
                return new PathEntry(this.reference, this.index);
            }

            public String toString() {
                return "PayloadTraverser.PathEntry.PathEntryBuilder(reference=" + this.reference + ", index=" + this.index + ")";
            }

        }

    }

    public static class PayloadTraverserBuilder {

        private @NonNull BiConsumer<Payload, PayloadTraverserContext> processor;
        private @NonNull Predicate<EReference> predicate;

        PayloadTraverserBuilder() {
        }

        public PayloadTraverserBuilder processor(@NonNull BiConsumer<Payload, PayloadTraverserContext> processor) {
            this.processor = processor;
            return this;
        }

        public PayloadTraverserBuilder predicate(@NonNull Predicate<EReference> predicate) {
            this.predicate = predicate;
            return this;
        }

        public PayloadTraverser build() {
            return new PayloadTraverser(this.processor, this.predicate);
        }

        public String toString() {
            return "PayloadTraverser.PayloadTraverserBuilder(processor=" + this.processor + ", predicate=" + this.predicate + ")";
        }

    }

}
