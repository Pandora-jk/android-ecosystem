---
name: "01. Core Contracts Module"
about: "Create shared Gradle module with URI constants"
title: "core:contracts: Create shared Gradle module with URI constants"
labels: "enhancement, core, contracts"
assignees: ""
---

## Task
Create the `core:contracts` Gradle module that defines shared URIs and data classes.

## Acceptance Criteria
- [ ] Create `core:contracts` module with `build.gradle.kts`
- [ ] Add `EcosystemContracts` object with URI constants for Contacts, Media, Locations
- [ ] Add data classes: `Contact`, `Photo`, `Location`
- [ ] Publish to Maven Local

## Estimated Size
~100 lines of Kotlin

## References
- See: `docs/ECOSYSTEM-ARCHITECTURE.md`
- Parent: Phase 1 of ecosystem plan
