package hu.blackbelt.judo.dao.api;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DAO<ID> {

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
     * Get instance of a given mapped transfer object type by the given identifier.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @param id mapped transfer object
     * @return return the optional payload
     */
    Optional<Payload> getByIdentifier(EClass clazz, ID identifier);

    /**
     * Get instances of a given mapped transfer object type by the given identifiers.
     * <p>
     * This operation can be used by JCL (expression) and custom Java sources.
     *
     * @param clazz mapped transfer object type
     * @param id mapped transfer object
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
     * @param id        instance ID to delete
     */
    void deleteReferencedInstancesOf(EClass clazz, EReference reference, ID id);

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
     * @param payload        instance to update (containing IDs of referenceToSet only)
     * @return updated references in the given instance
     */
    void setReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, Payload payload);

    /**
     * Unset reference in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#unset).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @return updated references in the given instance
     */
    void unsetReferenceOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet);

    /**
     * Add all references in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#addAll).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param payload        instance to update (containing IDs of referenceToSet only)
     * @return updated references in the given instance
     */
    void addAllReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, Payload payload);

    /**
     * Remove all references in a mapped transfer object of a given reference from a given mapped transfer object.
     * <p>
     * This operation can be used by bound operations (TransferObjectRelation#removeAll).
     *
     * @param id             mapped transfer object ID in which the instance to update can be found
     * @param reference      transfer object relation that the instance to update is linked in (pre condition)
     * @param referenceToSet transfer object relation to set
     * @param payload        instance to update (containing IDs of referenceToSet only)
     * @return updated references in the given instance
     */
    void removeAllReferencesOfNavigationInstanceAt(ID id, EReference reference, EReference referenceToSet, Payload payload);

    /**
     * Get range of a given transfer object relation.
     * <p>
     * This operation can be used by unbound operations (TransferObjectRelation#getRange).
     *
     * @param reference transfer object relation
     * @param payload   payload of the instance in which the reference is
     * @return list of instances that can be used by references.
     */
    List<Payload> getRange(EReference reference, Payload payload);

    /**
     * Get template of a given mapped transfer object type.
     * <p>
     * This operation can be used by unbound operations (TransferObjectType#getTemplate).
     *
     * @param clazz mapped transfer object type
     * @return template containing default values
     */
    Payload getTemplate(EClass clazz);
}
