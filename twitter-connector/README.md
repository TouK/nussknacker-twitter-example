# Introduction

This connector uses the twitter streaming api to listen for status update messages and
convert them to a Kafka Connect struct on the fly. The goal is to match as much of the
Twitter Tweet object as possible.

# Configuration

## TwitterSourceConnector

This Twitter Source connector is used to pull data from Twitter in realtime.

```properties
name=connector1
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.twitter.TwitterSourceConnector
# Set these required values
twitter.bearerToken=
kafka.tweets.topic=
# And optionally these values
filter.rule=
```

| Name                | Description                                                                                                        | Type     | Default | Valid Values | Importance |
|---------------------|--------------------------------------------------------------------------------------------------------------------|----------|---------|--------------|------------|
| filter.rule         | Filtering rules (https://developer.twitter.com/en/docs/twitter-api/tweets/filtered-stream/integrate/build-a-rule). | string   |         |              | high       |
| kafka.tweets.topic  | Kafka topic to write the tweets to.                                                                                | string   |         |              | high       |
| twitter.bearerToken | OAuth2 Bearer token with at least Elevated Twitter API access level                                                | password |         |              | high       |

# Schemas

Schema is almost the same as `#/components/schemas/Tweet` json schema included in https://api.twitter.com/2/openapi.json - 
the only difference is that it is translated to Kafka connect schema. See `com.github.jcustenborder.kafka.connect.twitter.TweetConverter`
for details.
