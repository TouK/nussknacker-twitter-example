package com.github.jcustenborder.kafka.connect.twitter;

import com.twitter.clientlib.JSON;
import com.twitter.clientlib.model.FullTextEntities;
import com.twitter.clientlib.model.HashtagEntity;
import com.twitter.clientlib.model.Tweet;
import com.twitter.clientlib.model.TweetEditControls;
import com.twitter.clientlib.model.UrlEntity;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.connect.data.Struct;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TweetConverterTest2 {

    {
        // init twitter json deserializers
        new JSON();
    }

    @Test
    public void shouldConvertTweetWithPhoto() throws IOException {
        Tweet tweet = Tweet.fromJson(IOUtils.resourceToString("/sample_tweets/with-photo.json", StandardCharsets.UTF_8));
        Struct result = TweetConverter2.convert(tweet);
        assertEquals("How times have changed https://t.co/gCxUkZ4kZC", result.getString(Tweet.SERIALIZED_NAME_TEXT));
        assertEquals(5, result
                .getStruct(Tweet.SERIALIZED_NAME_EDIT_CONTROLS)
                .getInt32(TweetEditControls.SERIALIZED_NAME_EDITS_REMAINING));
        assertEquals("https://t.co/gCxUkZ4kZC", result
                .getStruct(Tweet.SERIALIZED_NAME_ENTITIES)
                .<Struct>getArray(FullTextEntities.SERIALIZED_NAME_URLS)
                .get(0)
                .getString(UrlEntity.SERIALIZED_NAME_URL));
        assertEquals("everyone", result.getString(Tweet.SERIALIZED_NAME_REPLY_SETTINGS));
    }

    @Test
    public void shouldConvertTweetWithReference() throws IOException {
        Tweet tweet = Tweet.fromJson(IOUtils.resourceToString("/sample_tweets/with-reference.json", StandardCharsets.UTF_8));
        TweetConverter2.convert(tweet);
    }

    @Test
    public void shouldConvertReplyTweet() throws IOException {
        Tweet tweet = Tweet.fromJson(IOUtils.resourceToString("/sample_tweets/reply.json", StandardCharsets.UTF_8));
        TweetConverter2.convert(tweet);
    }

    @Test
    public void shouldConvertTweetWithHashtag() throws IOException {
        Tweet tweet = Tweet.fromJson(IOUtils.resourceToString("/sample_tweets/with-hashtag.json", StandardCharsets.UTF_8));
        Struct result = TweetConverter2.convert(tweet);
        assertEquals("Bitcoin", result
                .getStruct(Tweet.SERIALIZED_NAME_ENTITIES)
                .<Struct>getArray(FullTextEntities.SERIALIZED_NAME_HASHTAGS)
                .get(0)
                .getString(HashtagEntity.SERIALIZED_NAME_TAG));
    }

}
