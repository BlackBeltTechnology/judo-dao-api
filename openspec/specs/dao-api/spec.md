# dao-api Specification

## Purpose

Defines the core Data Access Object interface (`DAO`), the universal data transfer type (`Payload`/`PayloadImpl`), the validation contract (`PayloadValidator`/`ValidationResult`), and the identifier provider interface (`IdentifierProvider`) for the JUDO runtime. All operations are driven by EMF metamodel types.

## Architecture

Single package `hu.blackbelt.judo.dao.api` containing:
- `DAO` interface with inner classes `OrderBy`, `Seek`, `QueryCustomizer`
- `Payload` interface (extends `Map<String, Object>`) with factory methods
- `PayloadImpl` implementation backed by `TreeMap`, auto-wrapping nested maps/collections
- `PayloadValidator` interface for three validation scopes (payload, reference, attribute)
- `ValidationResult` value class with code, level, location, and details
- `IdentifierProvider` interface for pluggable ID generation
- `JacksonNullKeySerializer` package-private helper for JSON serialization

## Requirements

### Requirement: CRUD Operations

The `DAO` interface SHALL provide create, read (get by identifier), update, and delete operations on mapped transfer object types identified by `EClass`.

#### Scenario: Create a transfer object instance
- **GIVEN** a mapped transfer object type (`EClass`) and a `Payload` with field values
- **WHEN** `DAO.create(EClass, Payload, QueryCustomizer)` is called
- **THEN** the instance is persisted and the created `Payload` (with generated ID) is returned

#### Scenario: Get by identifier
- **GIVEN** a mapped transfer object type and a `Serializable` identifier
- **WHEN** `DAO.getByIdentifier(EClass, Serializable)` is called
- **THEN** an `Optional<Payload>` is returned containing the instance if it exists, or empty otherwise

#### Scenario: Update a transfer object instance
- **GIVEN** a mapped transfer object type and a `Payload` with updated values
- **WHEN** `DAO.update(EClass, Payload, QueryCustomizer)` is called
- **THEN** the updated `Payload` is returned

#### Scenario: Delete a transfer object instance
- **GIVEN** a mapped transfer object type and an identifier
- **WHEN** `DAO.delete(EClass, Serializable)` is called
- **THEN** the instance is removed

### Requirement: Batch CRUD Operations

The `DAO` interface SHALL provide batch create (`createAll`), batch update (`updateAll`), batch delete (`deleteAll`), and batch get (`getByIdentifiers`, `searchByIdentifiers`) operations.

#### Scenario: Create multiple instances
- **GIVEN** an `EClass` and an `Iterable<Payload>` of instances
- **WHEN** `DAO.createAll(EClass, Iterable, QueryCustomizer)` is called
- **THEN** all instances are created and the resulting list of `Payload` is returned

### Requirement: Search and Count

The `DAO` interface SHALL support searching and counting instances using `QueryCustomizer` for filtering, ordering, pagination (seek), masking, and instance ID filtering.

#### Scenario: Search with filter and ordering
- **GIVEN** a mapped transfer object type and a `QueryCustomizer` with filter expression and `OrderBy` list
- **WHEN** `DAO.search(EClass, QueryCustomizer)` is called
- **THEN** a filtered and ordered `List<Payload>` is returned

#### Scenario: Count with filter
- **GIVEN** a mapped transfer object type and a `QueryCustomizer` with filter expression
- **WHEN** `DAO.count(EClass, QueryCustomizer)` is called
- **THEN** the number of matching instances is returned

### Requirement: Reference Management

The `DAO` interface SHALL support setting, unsetting, adding, and removing references between transfer object instances using `EReference`.

#### Scenario: Set a single reference
- **GIVEN** a transfer object relation (`EReference`), an owner ID, and a collection of referenced IDs
- **WHEN** `DAO.setReference(EReference, Serializable, Collection)` is called
- **THEN** the reference is set to the specified target(s)

#### Scenario: Unset a single reference
- **GIVEN** a non-many transfer object relation (`EReference`) and an owner ID
- **WHEN** `DAO.unsetReference(EReference, Serializable)` is called
- **THEN** the reference is cleared

### Requirement: Navigation Operations

The `DAO` interface SHALL support bound navigation — getting, searching, counting, creating, updating, and deleting instances relative to a specific owner and reference.

#### Scenario: Get navigation result
- **GIVEN** an owner ID and an `EReference`
- **WHEN** `DAO.getNavigationResultAt(Serializable, EReference)` is called
- **THEN** the related instances are returned as `List<Payload>`

#### Scenario: Create instance at navigation
- **GIVEN** an owner ID, an `EReference`, and a `Payload`
- **WHEN** `DAO.createNavigationInstanceAt(Serializable, EReference, Payload, QueryCustomizer)` is called
- **THEN** a new instance is created linked to the owner via the reference

### Requirement: Static Features and Defaults

The `DAO` interface SHALL support loading static features and default values for unmapped/mapped transfer object types.

#### Scenario: Get static features
- **GIVEN** an unmapped transfer object type (`EClass`)
- **WHEN** `DAO.getStaticFeatures(EClass)` is called
- **THEN** a `Payload` containing static attributes and embedded relations is returned

#### Scenario: Get defaults
- **GIVEN** a transfer object type
- **WHEN** `DAO.getDefaultsOf(EClass)` is called
- **THEN** a `Payload` with default values for all attributes is returned

### Requirement: Range Queries

The `DAO` interface SHALL support querying the valid range of values for a given reference, with optional marking of already-selected items.

#### Scenario: Get range of reference
- **GIVEN** an `EReference`, an owner `Payload`, a `QueryCustomizer`, and `markSelectedRangeItems=true`
- **WHEN** `DAO.getRangeOf(EReference, Payload, QueryCustomizer, boolean, boolean)` is called
- **THEN** a collection of valid `Payload` items is returned, with selected items marked

### Requirement: Payload Construction

The `Payload` interface SHALL provide static factory methods to construct payloads from maps, key-value pairs (up to 20), and entry varargs.

#### Scenario: Create payload from key-value pairs
- **WHEN** `Payload.map("name", "John", "age", 30)` is called
- **THEN** a `Payload` is returned with keys "name" and "age" mapped to respective values

#### Scenario: Create empty payload
- **WHEN** `Payload.empty()` is called
- **THEN** an empty `Payload` is returned

#### Scenario: Wrap existing map
- **GIVEN** a `Map<String, Object>` with nested maps
- **WHEN** `Payload.asPayload(map)` is called
- **THEN** nested maps are recursively wrapped as `Payload` instances

### Requirement: Payload Type-Safe Access

The `Payload` interface SHALL provide type-safe accessor methods for nested payloads, payload collections, and arbitrary typed values.

#### Scenario: Get nested payload
- **GIVEN** a `Payload` containing key "address" mapped to another `Payload`
- **WHEN** `payload.getAsPayload("address")` is called
- **THEN** the nested `Payload` is returned

#### Scenario: Get typed value
- **GIVEN** a `Payload` containing key "count" mapped to an `Integer`
- **WHEN** `payload.getAs(Integer.class, "count")` is called
- **THEN** the `Integer` value is returned

#### Scenario: Type mismatch throws
- **GIVEN** a `Payload` containing key "name" mapped to a `String`
- **WHEN** `payload.getAs(Integer.class, "name")` is called
- **THEN** an `IllegalArgumentException` is thrown

### Requirement: Payload Transient Fields

`PayloadImpl` SHALL exclude fields prefixed with `__$` from equality comparison while keeping them accessible in the map.

#### Scenario: Equality ignores transient fields
- **GIVEN** two payloads with identical non-transient fields but different `__$`-prefixed fields
- **WHEN** `equals()` is called
- **THEN** the payloads are considered equal

### Requirement: Payload Ordered Keys

`PayloadImpl` SHALL store entries in a `TreeMap` to guarantee consistent key ordering.

#### Scenario: Keys are ordered
- **GIVEN** a payload created from a map with keys in arbitrary order
- **WHEN** iterating the payload's key set
- **THEN** keys are returned in natural (alphabetical) sort order

### Requirement: Payload Null Key Rejection

`PayloadImpl` SHALL reject null keys on construction.

#### Scenario: Null key throws
- **GIVEN** a map containing a null key
- **WHEN** `new PayloadImpl(map)` is called
- **THEN** an `IllegalArgumentException` is thrown with message "Payload contains null key(s)"

### Requirement: Payload Validation

The `PayloadValidator` interface SHALL provide methods to validate payloads, references, and attributes against EMF-typed transfer object definitions.

#### Scenario: Validate a payload
- **GIVEN** a transfer object type (`EClass`), a `Payload`, a validation context, and `throwValidationException=false`
- **WHEN** `PayloadValidator.validatePayload(EClass, Payload, Map, boolean)` is called
- **THEN** a `Collection<ValidationResult>` is returned with any validation errors/warnings

### Requirement: Validation Result Structure

`ValidationResult` SHALL carry a `code` (String), `level` (ERROR or WARNING), `location` (Object), and a `details` map.

#### Scenario: Build a validation result
- **WHEN** `ValidationResult.builder().code("REQUIRED").level(Level.ERROR).location(attribute).detail("field", "name").build()` is called
- **THEN** a `ValidationResult` is created with the specified code, level, location, and details entry

### Requirement: Identifier Provider

The `IdentifierProvider` interface SHALL provide a generic mechanism to obtain identifiers with their type and name.

#### Scenario: Get identifier
- **GIVEN** an `IdentifierProvider` implementation
- **WHEN** `get()`, `getType()`, and `getName()` are called
- **THEN** the identifier value, its `Class<? extends Serializable>` type, and its name are returned

### Requirement: QueryCustomizer Builder

The `QueryCustomizer` inner class SHALL support building query customizations with filter, order-by list, seek (limit/offset/reverse/lastItem), withoutFeatures flag, mask, parameters, and instance IDs.

#### Scenario: Build a paginated query
- **WHEN** `QueryCustomizer.builder().filter("name like '%John%'").orderBy(orderBy).seek(Seek.builder().limit(10).offset(0).build()).build()` is called
- **THEN** a `QueryCustomizer` is created with the specified filter, ordering, and pagination
