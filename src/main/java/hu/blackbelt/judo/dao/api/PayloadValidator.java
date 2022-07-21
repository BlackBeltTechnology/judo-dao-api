package hu.blackbelt.judo.dao.api;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import java.util.*;

public interface PayloadValidator {

    Collection<ValidationResult> validateInsert(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validateUpdate(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validateSetReference(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validateUnsetReference(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validateAddReference(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validateRemoveReference(final EClass transferObjectType, Payload input);

    Collection<ValidationResult> validatePayload(final EClass transferObjectType, final Payload input, final Map<String, Object> validationContext, boolean throwValidationException);

    Collection<ValidationResult> validateReference(final EReference reference, final Payload instance, final Map<String, Object> validationContext, final boolean ignoreInvalidValues);

    Collection<ValidationResult> validateAttribute(final EAttribute attribute, final Payload instance, final Map<String, Object> validationContext);

}
