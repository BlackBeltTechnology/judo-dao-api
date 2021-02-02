package hu.blackbelt.judo.dao.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
     * Get default values of a given transfer object type.
     *
     * @param clazz transfer object type
     * @return payload of default values
     */
    Payload getDefaultsOf(EClass clazz);

    /**
     * Get range of a given transfer object relation.
     *
     * @param reference transfer objet relation
     * @param payload   owner data of relation
     * @param filter      filter expression
     * @param orderByList order by clauses
     * @param seek        seek parameters (limit, last item, reverse pagination)
     * @return list of possible item(s)
     */
    Collection<Payload> getRangeOf(EReference reference, Payload payload, String filter, List<OrderBy> orderByList, Seek seek);

    /**
     * Get all instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @return list of instances
     */
    List<Payload> getAllOf(EClass clazz);

    /**
     * Search instances of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz       mapped transfer object type
     * @param filter      filter expression
     * @param orderByList order by clauses
     * @param seek        seek parameters (limit, last item, reverse pagination)
     * @returnlist of instances
     */
    List<Payload> search(EClass clazz, String filter, List<OrderBy> orderByList, Seek seek);

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
     * Create a new instance of a given mapped transfer object type.
     * <p>
     * This operation can be used by JCL (create), exposed graphs (ExposedGraph#create) and custom Java sources. Mapped
     * transfer object must have a filter to restrict which kind of instances can be created by exposed services.
     *
     * @param clazz   mapped transfer object type
     * @param payload instance to create
     * @return created instance
     */
    Payload create(EClass clazz, Payload payload);

    /**
     * Update a mapped transfer object.
     * <p>
     * This operation can be used by JCL (update) and custom Java sources.
     *
     * @param clazz   mapped transfer object type
     * @param payload instance to update
     * @return updated instance
     */
    Payload update(EClass clazz, Payload payload);

    /**
     * Delete a mapped transfer object.
     * <p>
     * This operation can be used by JCL (delete) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @param id    instance ID to delete
     */
    void delete(EClass clazz, ID id);

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
     * Get all mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference static navigation
     * @param clazz     mapped transfer object type
     * @return all instances that are matching a static navigation
     */
    List<Payload> getAllReferencedInstancesOf(EReference reference, EClass clazz);

    /**
     * Search mapped transfer objects of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#get).
     *
     * @param reference   static navigation
     * @param clazz       mapped transfer object type
     * @param orderByList order by clauses
     * @oaram filter    filter expression
     * @param seek        seek parameters (limit, last item, reverse pagination)
     * @return all instances that are matching a static navigation
     */
    List<Payload> searchReferencedInstancesOf(EReference reference, EClass clazz, String filter, List<OrderBy> orderByList, Seek seek);

    /**
     * Update a mapped transfer object of a given reference (static navigation).
     * <p>
     * This operation can be used by exposed graphs (ExposedGraph#update).
     *
     * @param clazz     mapped transfer object type
     * @param reference static navigation
     * @param payload   instance to update
     * @return updated instance
     */
    Payload updateReferencedInstancesOf(EClass clazz, EReference reference, Payload payload);

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
     * Add all references in a mapped transfer object of a given reference of a given reference (static navigation).
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
     * Remove all references in a mapped transfer object of a given reference of a given reference (static navigation).
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
     * Get all instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id        ID of source mapped transfer object
     * @param reference transfer object reference
     * @return list of instances
     */
    List<Payload> getNavigationResultAt(ID id, EReference reference);

    /**
     * Search instances of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#get).
     *
     * @param id          ID of source mapped transfer object
     * @param reference   transfer object reference
     * @param filter      filter expression
     * @param orderByList order by clauses
     * @param seek        seek parameters (limit, last item, reverse pagination)
     * @return list of instances
     */
    List<Payload> searchNavigationResultAt(ID id, EReference reference, String filter, List<OrderBy> orderByList, Seek seek);

    /**
     * Create a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#create).
     *
     * @param id        mapped transfer object ID in which the new instance will be created
     * @param reference transfer object relation that the new instance will be linked to
     * @param payload   instance to create
     * @return created instance
     */
    Payload createNavigationInstanceAt(ID id, EReference reference, Payload payload);

    /**
     * Update a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#update).
     *
     * @param id        mapped transfer object ID in which the instance to update can be found
     * @param reference transfer object relation that the instance to update is linked in (pre condition)
     * @param payload   instance to update
     * @return updated instance
     */
    Payload updateNavigationInstanceAt(ID id, EReference reference, Payload payload);

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

        private boolean reverse;

        private Payload lastItem;
    }
}
