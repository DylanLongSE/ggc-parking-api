# ggc-parking-api

[![CI](https://github.com/DylanLongSE/ggc-parking-api/actions/workflows/ci.yml/badge.svg)](https://github.com/DylanLongSE/ggc-parking-api/actions/workflows/ci.yml)

Spring Boot REST API for the GGC smart parking project. It receives vehicle counts from a Raspberry Pi running YOLO detection and stores them in Supabase (PostgreSQL). The Next.js frontend polls this API to display real-time lot availability.

## Running locally

Prerequisites: Java 17+

```bash
# Set required environment variables (or rely on defaults in application.yml)
export SUPABASE_URL=https://your-project.supabase.co/
export SUPABASE_SECRET_KEY=your-service-role-key
export PI_SHARED_SECRET=your-shared-secret

# Build and run
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`. Hit `http://localhost:8080/ping` to verify — you should get back `pong`.

Swagger docs are available at `http://localhost:8080/docs`.

## Environment variables

| Variable | Description | Default |
|---|---|---|
| `PORT` | Server port | `8080` |
| `SUPABASE_URL` | Your Supabase project URL | — |
| `SUPABASE_SECRET_KEY` | Supabase service role key | — |
| `PI_SHARED_SECRET` | Bearer token the Pi sends to authenticate | — |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed origins | `http://localhost:3000,http://localhost:5500` |

## API endpoints

### Health check

```
GET /ping
```

Returns `pong`. No authentication required.

### Ingest parking count

```
POST /api/v1/lots/{lotId}/counts
```

The Pi calls this endpoint to report how many spaces are occupied in a lot. Requires a bearer token matching `PI_SHARED_SECRET`.

**Example request:**

```bash
curl -X POST http://localhost:8080/api/v1/lots/lot_a/counts \
  -H "Authorization: Bearer your-shared-secret" \
  -H "Content-Type: application/json" \
  -d '{
    "occupied": 25,
    "reason": "sensor_update",
    "source": "pi_01"
  }'
```

**Request body:**

| Field | Type | Required | Description |
|---|---|---|---|
| `occupied` | int (>= 0) | yes | Number of occupied spaces |
| `occupiedIds` | int[] | no | IDs of occupied spaces |
| `reason` | string | no | Why this update was sent |
| `source` | string | no | Which device sent it |
| `timestamp` | ISO-8601 | no | When the count was taken (defaults to now) |

**Example response (200):**

```json
{
  "lotId": "lot_a",
  "occupied": 25,
  "previous": 24,
  "wroteHistory": true
}

```

`previous` is `null` on the first write. `wroteHistory` is `true` when the count actually changed.

**Error responses:**
- `401` — missing or invalid bearer token
- `400` — `occupied` is missing or negative

## Docker

```bash
# Build
docker build -t ggc-parking-api .

# Run
docker run -p 8080:8080 \
  -e SUPABASE_URL=https://your-project.supabase.co/ \
  -e SUPABASE_SECRET_KEY=your-key \
  -e PI_SHARED_SECRET=your-secret \
  ggc-parking-api
```

## Project structure

```
src/main/java/com/example/parking/
├── GgcParkingApiApplication.java    # Entry point
├── config/
│   ├── CorsConfig.java              # CORS settings
│   ├── PingController.java          # GET /ping
│   └── RestTemplateConfig.java      # HTTP client bean
├── controller/
│   └── ParkingIngestController.java # POST /api/v1/lots/{lotId}/counts
├── dto/
│   ├── CountIngestRequest.java      # Incoming request body
│   ├── IngestResponse.java          # Outgoing response
│   └── UpsertResult.java            # Supabase RPC result mapping
└── service/
    ├── IngestService.java           # Business logic
    └── SupabaseRpcClient.java       # Calls Supabase RPC function
```
