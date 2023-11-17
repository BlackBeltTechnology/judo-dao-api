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

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import java.util.*;

public interface DAO<ID> {

    /**
     * Load static features (attributes and embedded relations) of an unmapped transfer object type.
     *
     * @param clazz unmapped transfer object type
     * @return payload of loaded static data
     */
    Payload getStaticFeatures(EClass clazz);

    /**
     * Get static data defined as derived expression.
     *
     * @param attribute transfer attribute
     * @return value of static data
     */
    Payload getStaticData(EAttribute attribute);

    /**
     * Get static data defined as derived expression.
     *
     * @param attribute  transfer attribute
     * @param parameters query parameters
     * @return value of static data
     */
    Payload getParameterizedStaticData(EAttribute attribute, Map<String, Object> parameters);

    /**
     * Get default values of a given transfer object type.
     *
     * @param clazz transfer object type
     * @return payload of default values
     */
    Payload getDefaultsOf(EClass clazz);

    /**
     * Get range of a given transfer object relation.
     *
     * @param reference       transfer objet relation
     * @param payload         owner data of relation
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @param stateful        allow create a new instance or update the existing instance of the 'payload'
     * @return list of possible item(s)
     */
    Collection<Payload> getRangeOf(EReference reference, Payload payload, QueryCustomizer<ID> queryCustomizer, boolean stateful);

    /**
     * Get range's count of a given transfer object relation.
     *
     * @param reference       transfer objet relation
     * @param payload         owner data of relation
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @param stateful        allow create a new instance or update the existing instance of the 'payload'
     * @return number of possible item(s)
     */
    long countRangeOf(EReference reference, Payload payload, QueryCustomizer<ID> queryCustomizer, boolean stateful);

    /**
     * Get instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @return list of instances
     */
    List<Payload> getAllOf(EClass clazz);

    /**
     * Count instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @return number of instances
     */
    long countAllOf(EClass clazz);

    /**
     * Search instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return list of instances
     */
    List<Payload> search(EClass clazz, QueryCustomizer<ID> queryCustomizer);

    /**
     * Count instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return number of instances
     */
    long count(EClass clazz, QueryCustomizer<ID> queryCustomizer);


    /**
     * Get instance of a given mapped transfer object type by the given identifier.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz      mapped transfer object type
     * @param identifier mapped transfer object
     * @return return the optional payload
     */
    Optional<Payload> getByIdentifier(EClass clazz, ID identifier);

    /**
     * Get instance of a given mapped transfer object type by the given identifier.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param identifier      mapped transfer object
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return return the optional payload
     */
    Optional<Payload> searchByIdentifier(EClass clazz, ID identifier, QueryCustomizer<ID> queryCustomizer);

    /**
     * Checks whether an instance of a given mapped transfer object type with the given identifier exists.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz      mapped transfer object type
     * @param identifier mapped transfer object
     * @return returns whether the given instance exists
     */
    boolean existsById(EClass clazz, ID identifier);

    /**
     * Get (entity) metadata of a given mapped transfer object type by identifier.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz      mapped transfer object type
     * @param identifier mapped transfer object
     * @return payload containing metadata (including entity type, version, etc.)
     */
    Optional<Payload> getMetadata(EClass clazz, ID identifier);

    /**
     * Get instances of a given mapped transfer object type by the given identifiers.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz       mapped transfer object type
     * @param identifiers mapped transfer object
     * @return list of instances
     */
    List<Payload> getByIdentifiers(EClass clazz, Collection<ID> identifiers);

    /**
     * Get instances of a given mapped transfer object type by the given identifiers.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param identifiers     mapped transfer object
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return list of instances
     */
    List<Payload> searchByIdentifiers(EClass clazz, Collection<ID> identifiers, QueryCustomizer<ID> queryCustomizer);

    /**
     * Create a new instance of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (create), exposed graphs (ExposedGraph#create) and custom Java sources. Mapped
     * transfer object must have a filter to restrict which kind of instances can be created by exposed services.
     *
     * @param clazz           mapped transfer object type
     * @param payload         instance to create
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return created instance
     */
    Payload create(EClass clazz, Payload payload, QueryCustomizer<ID> queryCustomizer);


    /**
     * Create a new instance of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (create), exposed graphs (ExposedGraph#create) and custom Java sources. Mapped
     * transfer object must have a filter to restrict which kind of instances can be created by exposed services.
     *
     * @param clazz           mapped transfer object type
     * @param payloads         instance to create
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return created instance
     */
    List<Payload> createAll(EClass clazz, Iterable<Payload> payloads, QueryCustomizer<ID> queryCustomizer);

    /**
     * Update a mapped transfer object.
     * <p>
     * This operation can be used by JCL (update) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param payload         instance to update
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return updated instance
     */
    Payload update(EClass clazz, Payload payload, QueryCustomizer<ID> queryCustomizer);

    /**
     * Update a mapped transfer object.
     * <p>
     * This operation can be used by JCL (update) and custom Java sources.
     *
     * @param clazz           mapped transfer object type
     * @param payloads         instance to update
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return updated instance
     */
    List<Payload> updateAll(EClass clazz, Iterable<Payload> payloads, QueryCustomizer<ID> queryCustomizer);

    /**
     * Delete a mapped transfer object.
     * <p>
     * This operation can be used by JCL (delete) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @param ids    instance ID to delete
     */
    void delete(EClass clazz, ID ids);

    /**
     * Delete a mapped transfer object.
     * <p>
     * This operation can be used by JCL (delete) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @param id    instance ID to delete
     */
    void deleteAll(EClass clazz, Iterable<ID> id);

    /**
     * Set references of a given mapped transfer object.
     * <p>
     * This operation can be used by JCL (:=) and custom Java sources.
     *
     * @param reference     transfer object relation
     * @param id            mapped transfer object (in which the reference to set is)
     * @param referencedIds referenced element IDs (to set), collection must contain only one instance if reference is single.
     */
    void setReference(EReference reference, ID id, Collection<ID> referencedIds);

    /**
     * Unset references of a given mapped transfer object.
     * <p>
     * This operation can be used by JCL (unset) and custom Java sources.
     *
     * @param reference transfer object relation, it must not be many
     * @param id        mapped transfer object (in which the reference to unset is)
     */
    void unsetReference(EReference reference, ID id);

    /**
     * Set references of a given mapped transfer object.
     * <p>
     * This operation can be used by JCL (+=) and custom Java sources.
     *
     * @param reference     transfer object relation, it must be many
     * @param id            mapped transfer object (in which the reference to add is)
     * @param referencedIds referenced element IDs (to add)
     */
    void addReferences(EReference reference, ID id, Collection<ID> referencedIds);

    /**
     * Set references of a given mapped transfer object.
     * <p>
     * This operation can be used by JCL (-=) and custom Java sources.
     *
     * @param reference     transfer object relation, it must be many
     * @param id            mapped transfer object (in which the reference to remove is)
     * @param referencedIds referenced element IDs (to remove)
     */
    void removeReferences(EReference reference, ID id, Collection<ID> referencedIds);

    /**
     * Get mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference static navigation
     * @param clazz     mapped transfer object type
     * @return instances that are matching a static navigation
     */
    List<Payload> getAllReferencedInstancesOf(EReference reference, EClass clazz);

    /**
     * Count mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference static navigation
     * @param clazz     mapped transfer object type
     * @return number of instances that are matching a static navigation
     */
    long countAllReferencedInstancesOf(EReference reference, EClass clazz);

    /**
     * Search mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference       static navigation
     * @param clazz           mapped transfer object type
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return instances that are matching a static navigation
     */
    List<Payload> searchReferencedInstancesOf(EReference reference, EClass clazz, QueryCustomizer<ID> queryCustomizer);

    /**
     * Count mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference       static navigation
     * @param clazz           mapped transfer object type
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return number of instances that are matching a static navigation
     */
    long countReferencedInstancesOf(EReference reference, EClass clazz, QueryCustomizer<ID> queryCustomizer);

    /**
     * Update a mapped transfer object of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#update).
     *
     * @param clazz           mapped transfer object type
     * @param reference       static navigation
     * @param payload         instance to update
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return updated instance
     */
    Payload updateReferencedInstancesOf(EClass clazz, EReference reference, Payload payload, QueryCustomizer<ID> queryCustomizer);

    /**
     * Delete a mapped transfer object of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#delete).
     *
     * @param clazz     mapped transfer object type
     * @param reference static navigation
     * @param payload   instance to delete
     */
    void deleteReferencedInstancesOf(EClass clazz, EReference reference, Payload payload);

    /**
     * Set reference in a mapped transfer object of a given reference of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#set).
     *
     * @param reference      static navigation
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances (collection is used for single relations too)
     * @return updated references in the given instance
     */
    void setReferencesOfReferencedInstancesOf(EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    /**
     * Unset reference in a mapped transfer object of a given reference of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#unset).
     *
     * @param reference      static navigation
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @return updated references in the given instance
     */
    void unsetReferencesOfReferencedInstancesOf(EReference reference, EReference referenceToSet, ID instanceId);

    /**
     * Add references in a mapped transfer object of a given reference of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#addAll).
     *
     * @param reference      static navigation
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances (collection is used for single relations too)
     * @return updated references in the given instance
     */
    void addAllReferencesOfReferencedInstancesOf(EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    /**
     * Remove references in a mapped transfer object of a given reference of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#removeAll).
     *
     * @param reference      static navigation
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances (collection is used for single relations too)
     * @return updated references in the given instance
     */
    void removeAllReferencesOfReferencedInstancesOf(EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    /**
     * Get instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id        ID of source mapped transfer object
     * @param reference transfer object reference
     * @return list of instances
     */
    List<Payload> getNavigationResultAt(ID id, EReference reference);

    /**
     * Count instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id        ID of source mapped transfer object
     * @param reference transfer object reference
     * @return number of instances
     */
    long countNavigationResultAt(ID id, EReference reference);

    /**
     * Search instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id              ID of source mapped transfer object
     * @param reference       transfer object reference
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return list of instances
     */
    List<Payload> searchNavigationResultAt(ID id, EReference reference, QueryCustomizer<ID> queryCustomizer);

    /**
     * Count instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id              ID of source mapped transfer object
     * @param reference       transfer object reference
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return number of instances
     */
    long countNavigationResultAt(ID id, EReference reference, QueryCustomizer<ID> queryCustomizer);

    /**
     * Create a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#create).
     *
     * @param id              mapped transfer object ID in which the new instance will be created
     * @param reference       transfer object relation that the new instance will be linked to
     * @param payload         instance to create
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return created instance
     */
    Payload createNavigationInstanceAt(ID id, EReference reference, Payload payload, QueryCustomizer<ID> queryCustomizer);

    /**
     * Update a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#update).
     *
     * @param id              mapped transfer object ID in which the instance to update can be found
     * @param reference       transfer object relation that the instance to update is linked in (pre condition)
     * @param payload         instance to update
     * @param queryCustomizer query customizer (i.e. filtering, ordering, seeking)
     * @return updated instance
     */
    Payload updateNavigationInstanceAt(ID id, EReference reference, Payload payload, QueryCustomizer<ID> queryCustomizer);

    /**
     * Delete a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#delete).
     *
     * @param id        mapped transfer object ID in which the instance to delete can be found
     * @param reference transfer object relation that the instance to delete is linked in (pre condition)
     * @param payload   instance ID to delete
     */
    void deleteNavigationInstanceAt(ID id, EReference reference, Payload payload);

    /**
     * Set reference in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#set).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances (collection is used for single relations too)
     * @return updated references in the given instance
     */
    void setReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    /**
     * Unset reference in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#unset).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @return updated references in the given instance
     */
    void unsetReferenceOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, ID instanceId);

    /**
     * Add all references in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#addAll).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances
     * @return updated references in the given instance
     */
    void addAllReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    /**
     * Remove all references in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#removeAll).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param instanceId     instance to update
     * @param referencedIds  referenced instances
     * @return updated references in the given instance
     */
    void removeAllReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, ID instanceId, Collection<ID> referencedIds);

    @Getter
    @Builder
    class OrderBy {

        @NonNull
        private EAttribute attribute;

        private boolean descending;
    }

    @Getter
    @Builder
    class Seek {

        private int limit;

        @Builder.Default
        private int offset = -1;

        private boolean reverse;

        private Payload lastItem;
    }

    @Getter
    @Builder
    class QueryCustomizer<ID> {

        private String filter;

        @Singular("orderBy")
        private List<OrderBy> orderByList;

        private Seek seek;

        private boolean withoutFeatures;

        private Map<String, Object> mask;

        private Map<String, Object> parameters;

        private Collection<ID> instanceIds;
    }
}
