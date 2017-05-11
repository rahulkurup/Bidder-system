package com.yieldlab.ResponseParser;

import com.yieldlab.exception.InvalidResponseException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContentResponseParser implements ResponseParser<JSONObject, String> {

    private static final String CONTENT_FIELD = "content";
    private static final String BID_ID = "bid";
    private static final String SPLITTER = ":";
    /* Now to only support one tag in content, but when we support multiple. use comma to seperate */
    private static final String CONTENT_SEPERATOR = ",";

    @Override
    public String parse(List<JSONObject> response) {

        /* This is costly. But assuming that response from highest bid could be bad, we can still give result this way */
        /* If bidder response can be trusted, we can only find highest
        *  JSONObject bestBid = response.stream().max(Comparator.comparing(this::getBidAmount)).get(); */
        Optional<String> result = response.stream()
                .sorted(Comparator.comparing(this::getBidAmount).reversed())
                .map(this::formatOutPut)
                .filter(Objects::nonNull).findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        throw new InvalidResponseException();
    }

    private int getBidAmount(JSONObject object) {
        return (int) object.get(BID_ID);
    }

    private String getContent(String input, String tag) {
        return input.substring(0, input.indexOf(tag));
    }

    /* Generic implementation which would allow us to add new tags in content in future */
    private EnumMap<ResponseTags, String> getTagMap(String input) {
        EnumMap<ResponseTags, String> enumMap = new EnumMap<>(ResponseTags.class);
        Arrays.stream(input.split(CONTENT_SEPERATOR)).forEach(content -> {
               /* Check if the tag is valid and activated */
            Optional<ResponseTags> validTag = ResponseTags.getActivatedTags().stream().filter(tag -> content.contains(tag.getTag())).findFirst();
            if (validTag.isPresent() && !getContent(input, validTag.get().getTag()).isEmpty()) {
                String tagValue = getContent(input, validTag.get().getTag());
                enumMap.put(validTag.get(), getContent(input, validTag.get().getTag()));
            }
        });
        return enumMap;
    }

    private String formatOutPut(JSONObject object) {
        EnumMap<ResponseTags, String> map = getTagMap(object.getString(CONTENT_FIELD));
        /* We only support price now */
        if (map.get(ResponseTags.PRICE) == null) {
            return null;
        }
        return map.get(ResponseTags.PRICE) + SPLITTER + object.get(BID_ID);
    }
}
