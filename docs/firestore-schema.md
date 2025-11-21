# SMART AIR Firestore Data Schema
Version 1.0

This document defines the Firestore data model for the SMART AIR Android app. Since Firestore is schemaless, this file serves as the schema definition for the app.

---

## Conventions

- All timestamps are Firestore `timestamp` values unless otherwise stated.
- All IDs (e.g. `userId`) are Firestore document IDs (strings).
- Global log collections always include `parentId`, `childId`, and `timestamp`.
- Roles:
  - `"parent"`
  - `"provider"`
  - `"child"`

---

## 1 - Users

### Collection: `users`

Includes the top-level metadata for all authenticated accounts.

**Path**

```text
users/{userId}
```

### Fields

| Field        | Type      | Required | Notes                                  |
|--------------|-----------|----------|----------------------------------------|
| role         | string    | yes      | `"parent"`, `"child"`, or `"provider"` |
| email        | string    | no       | Child accounts do not need an email    |
| displayName  | string    | no       | Name shown in UI                       |
| createdAt    | timestamp | yes      | Account creation date                  |
| lastLoginAt  | timestamp | no       | Last login (updated on login)          |

### Example

```json
{
  "role": "parent",
  "email": "parent@example.com",
  "displayName": "John Doe",
  "createdAt": "2025-01-01T12:00:00Z",
  "lastLoginAt": "2025-02-01T09:00:00Z"
}
```

---

## 2 - Parent - Child Profiles

### Subcollection: `children`

Includes the child profiles owned and controlled by a given parent.  
Children may optionally have a separate `users/{userId}` record.

**Path**

```text
users/{parentId}/children/{childId}
```

### Fields

| Field              | Type      | Required | Notes                                       |
|--------------------|-----------|----------|---------------------------------------------|
| name               | string    | yes      | Child’s name                                |
| dateOfBirth        | string    | no       | ISO date `"YYYY-MM-DD"` or timestamp        |
| notes              | string    | no       | Parent notes                                |
| createdAt          | timestamp | yes      | Child profile creation date                 |
| archived           | boolean   | no       | Hidden flag                                 |
| personalBestPEF    | number    | no       | Personal best peak flow (from R4)           |
| controllerSchedule | map       | no       | Expected controller doses per day (from R3) |

### Example

```json
{
  "name": "John",
  "dateOfBirth": "2013-05-10",
  "notes": "Prefers taking inhaler using spacer with mask",
  "createdAt": "2025-02-15T12:00:00Z",
  "archived": false,
  "personalBestPEF": 310,
  "controllerSchedule": { "morning": true, 
                          "evening": true }
}
```

---

## 3 - Selective Sharing

### Subcollection: `providerLinks`

Information sharing settings controlled by the Parent.

**Path**

```text
users/{parentId}/children/{childId}/providerLinks/{providerId}
```

### Fields

| Field               | Type      | Required | Notes                             |
|---------------------|-----------|----------|-----------------------------------|
| providerId          | string    | yes      | Must match a `users/{providerId}` |
| status              | string    | yes      | `"active"` or `"inactive"`        |
| shareRescueLogs     | boolean   | yes      | Rescue medication logs            |
| shareControllerLogs | boolean   | yes      | Daily controller use logs         |
| shareSymptoms       | boolean   | yes      | Symptom logs                      |
| shareTriggers       | boolean   | yes      | Trigger logs                      |
| sharePEF            | boolean   | yes      | Peak flow logs                    |
| shareTriage         | boolean   | yes      | Triage incidents                  |
| shareCharts         | boolean   | yes      | Summaries/charts based on logs    |
| createdAt           | timestamp | yes      | Link creation date                |
| updatedAt           | timestamp | yes      | Last sharing change               |

### Example

```json
{
  "providerId": "provider1", 
  "status": "active",
  "shareRescueLogs": true,
  "shareControllerLogs": true,
  "shareSymptoms": true,
  "shareTriggers": false,
  "sharePEF": true,
  "shareTriage": true,
  "shareCharts": true,
  "createdAt": "2025-02-20T16:00:00Z",
  "updatedAt": "2025-02-20T16:00:00Z"
}
```

---

## 4 - Provider Invites

### Collection: `providerInvites`

Temporary invite codes for providers.

**Path**

```text
providerInvites/{inviteCode}
```

### Fields

| Field         | Type      | Required | Notes                                              |
|---------------|-----------|----------|----------------------------------------------------|
| parentId      | string    | yes      | Parent who created the invite                      |
| childId       | string    | yes      | Child whose info is being shared                   |
| providerEmail | string    | no       | Provider email                                     |
| status        | string    | yes      | `"pending"`, `"used"`, `"revoked"`, or `"expired"` |
| createdAt     | timestamp | yes      | Invite creation time                               |
| expiresAt     | timestamp | yes      | Expires 7 days after creation                      |
| usedAt        | timestamp | no       | Set when a provider links successfully             |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "providerEmail": "doctor@gmail.com",
  "status": "pending",
  "createdAt": "2025-02-20T16:00:00Z",
  "expiresAt": "2025-02-27T16:00:00Z",
  "usedAt": null
}
```

---

## 5 - Global Log Collections

All log collections are top-level.

Each contains:
- `parentId`
- `childId`
- `timestamp`

Allows for:
- Parent only access using `parentId`
- Provider access by checking `parentId`, `childId`, and `providerLinks`
- Querying for history, dashboards, reports, etc.

---

## 5.1 - Controller Medication Logs

### Collection: `controllerLogs`

**Path**

```text
controllerLogs/{logId}
```

### Fields

| Field     | Type      | Required | Notes                          |
|-----------|-----------|----------|--------------------------------|
| parentId  | string    | yes      |                                |
| childId   | string    | yes      |                                |
| timestamp | timestamp | yes      | When controller does is taken  |
| taken     | boolean   | yes      | Whether dose was taken or not  |
| doseCount | number    | no       | Number of puffs / total dosage |
| createdBy | string    | no       | `"parent"` or `"child"`        |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-21T08:00:00Z",
  "taken": true,
  "doseCount": 1,
  "createdBy": "parent"
}
```

---

## 5.2 - Rescue Medication Logs

### Collection: `rescueLogs`

**Path**

```text
rescueLogs/{logId}
```

### Fields

| Field        | Type      | Required | Notes                              |
|--------------|-----------|----------|------------------------------------|
| parentId     | string    | yes      |                                    |
| childId      | string    | yes      |                                    |
| timestamp    | timestamp | yes      | Time rescue is used                |
| medicationId | string    | yes      | Rescue medication ID               |
| puffs        | number    | yes      | Number of doses taken              |
| preFeeling   | string    | no       | `"good"`, `"neutral"`, or `"bad"`  |
| postFeeling  | string    | no       | `"better"`, `"same"`, or `"worse"` |
| createdBy    | string    | no       | `"parent"` or `"child"`            |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-21T09:30:00Z",
  "medicationId": "salbutamol",
  "puffs": 2,
  "preFeeling": "bad",
  "postFeeling": "better",
  "createdBy": "child"
}
```

---

## 5.3 - Symptom Logs

### Collection: `symptomLogs`

**Path**

```text
symptomLogs/{logId}
```

### Fields

| Field         | Type      | Required | Notes                                            |
|---------------|-----------|----------|--------------------------------------------------|
| parentId      | string    | yes      |                                                  |
| childId       | string    | yes      |                                                  |
| timestamp     | timestamp | yes      |                                                  |
| nightWaking   | boolean   | no       | Woke up due to symptoms                          |
| activityLimit | boolean   | no       | Reduced ability to play/exercise due to symptoms |
| cough         | string    | no       | `"none"`, `"mild"`, `"moderate"`, or `"severe"`  |
| wheeze        | string    | no       | `"none"`, `"mild"`, `"moderate"`, or `"severe"`  |
| createdBy     | string    | no       | `"parent"` or `"child"`                          |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-21T21:00:00Z",
  "nightWaking": false,
  "activityLimit": true,
  "cough": "mild",
  "wheeze": "severe",
  "createdBy": "child"
}
```

---

## 5.4 - Trigger Logs

### Collection: `triggerLogs`

**Path**

```text
triggerLogs/{logId}
```

### Fields

| Field     | Type          | Required | Notes                             |
|-----------|---------------|----------|-----------------------------------|
| parentId  | string        | yes      |                                   |
| childId   | string        | yes      |                                   |
| timestamp | timestamp     | yes      |                                   |
| triggers  | array<string> | no       | e.g. `"dust"`, `"exercise"`, etc. |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-21T21:05:00Z",
  "triggers": ["exercise", "dust"]
}
```

---

## 5.5 - Peak FLow (PEF) Logs

### Collection: `pefLogs`

**Path**

```text
pefLogs/{logId}
```

### Fields

| Field     | Type      | Required | Notes                             |
|-----------|-----------|----------|-----------------------------------|
| parentId  | string    | yes      |                                   |
| childId   | string    | yes      |                                   |
| timestamp | timestamp | yes      |                                   |
| pef       | number    | yes      | Peak flow value                   |
| zone      | string    | no       | `"green"`, `"yellow"`, or `"red"` |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-21T07:45:00Z",
  "pef": 280,
  "zone": "yellow"
}
```

---

## 5.6 - Triage Incident

### Collection: `triageIncidents`

**Path**

```text
triageIncidents/{logId}
```

### Fields

| Field             | Type       | Required | Notes                                                  |
|-------------------|------------|----------|--------------------------------------------------------|
| parentId          | string     | yes      |                                                        |
| childId           | string     | yes      |                                                        |
| timestamp         | timestamp  | yes      | Start time of triage flow                              |
| redFlags          | map        | no       | Red-flag symptoms (see example)                        |
| recentRescuePuffs | number     | no       | Number of rescue puffs taken immediately before triage |
| optionalPEF       | number     | no       | PEF during triage                                      |
| initialZone       | string     | no       | Zone at start: `"green"`, `"yellow"`, or `"red"`       |
| outcome           | string     | no       | e.g. `"call_emergency"`                                |
| escalation        | boolean    | no       | Flow escalated or not                                  |
| logs              | array<map> | no       | Steps taken in triage flow                             |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "timestamp": "2025-02-22T01:15:00Z",
  "redFlags": {
    "difficultySpeaking": false,
    "chestRetractions": true,
    "cyanosis": false
  },
  "recentRescuePuffs": 4,
  "optionalPEF": 200,
  "initialZone": "red",
  "outcome": "call_emergency",
  "escalation": true,
  "logs": [
    { "step": "start", "time": "2025-02-22T01:15:00Z" },
    { "step": "recheck", "time": "2025-02-22T01:25:00Z" }
  ]
}
```

---

## 5.7 - Inventory

### Collection: `inventory`

**Path**

```text
inventory/{itemId}
```

### Fields

| Field           | Type      | Required | Notes                                              |
|-----------------|-----------|----------|----------------------------------------------------|
| parentId        | string    | yes      |                                                    |
| childId         | string    | yes      |                                                    |
| medicationId    | string    | yes      | Medication ID                                      |
| purchaseDate    | timestamp | no       |                                                    |
| expiryDate      | timestamp | no       | Used for alerts of expired/expiring medicine       |
| remainingAmount | number    | no       | e.g. puffs remaining                               |
| alertLow        | boolean   | no       | Whether low-inventory alert has been raised or not |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "medicationId": "salbutamol",
  "purchaseDate": "2025-02-01T12:00:00Z",
  "expiryDate": "2026-02-01T12:00:00Z",
  "remainingAmount": 20,
  "alertLow": true
}
```

---

## 6 - Streaks

### Collection: `streaks`

**Path**

```text
streaks/{streakId}
```

### Fields

| Field         | Type      | Required | Notes                           |
|---------------|-----------|----------|---------------------------------|
| parentId      | string    | yes      |                                 |
| childId       | string    | yes      |                                 |
| type          | string    | yes      | `"controller"` or `"technique"` |
| currentStreak | number    | yes      | Number of consecutive days      |
| updatedAt     | timestamp | yes      | Last time streak was updated    |

### Example

```json
{
  "parentId": "parent1",
  "childId": "child1",
  "type": "controller",
  "currentStreak": 12,
  "updatedAt": "2025-02-21T23:59:00Z"
}
```

---

## 7 - Badges

### Collection: `badges`

**Path**

```text
badges/{badgeId}
```

### Fields

| Field     | Type      | Required | Notes                                                                  |
|-----------|-----------|----------|------------------------------------------------------------------------|
| parentId  | string    | yes      |                                                                        |
| childId   | string    | yes      |                                                                        |
| badgeType | string    | yes      | e.g. `"perfect_controller_week"`, `"ten_high_quality_technique"`, etc. |
| earnedAt  | timestamp | yes      | Date and time of badge being obtained                                  |

### Example

```json
{
  "parentId": "p1",
  "childId": "c1",
  "badgeType": "perfect_controller_week",
  "earnedAt": "2025-02-21T20:00:00Z"
}
```