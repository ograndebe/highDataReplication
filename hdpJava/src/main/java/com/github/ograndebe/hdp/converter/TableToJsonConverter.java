package com.github.ograndebe.hdp.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;

@Component
public class TableToJsonConverter {

    public String convert(Element table) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ArrayNode arrayNode = objectMapper.createArrayNode();
        ArrayList<String> columnNames = processColumnNames(table);

        Elements rows = table.select("tr");
        for (Element row : rows) {
            int columnId = 0;
            Elements columns = row.select("td");
            fixColumnNames(columnNames, columns.size());
            if (columns.size() > 0) {
                final ObjectNode objectNode = objectMapper.createObjectNode();
                for (Element column : columns) {
                    objectNode.put(columnNames.get(columnId), column.text());
                    columnId++;
                }
                arrayNode.add(objectNode);
            }
        }

        return arrayNode.toPrettyString();
    }

    private void fixColumnNames(ArrayList<String> columnNames, int size) {
        if (size > columnNames.size()) {
            int nextIndex = columnNames.size();
            while (nextIndex < size) columnNames.add(String.format("column%s",nextIndex++));
        }
    }


    private ArrayList<String> processColumnNames(Element table) {
        Elements columns = table.select("th");

        int columnId = 0;
        ArrayList<String> result = new ArrayList<>(columns.size());
        for (Element column : columns) {
            final String columnName = convertName(column.text(), columnId++);
            result.add(columnName);
        }

        return result;
    }

    private String convertName(String text, int i) {
        final String normalized = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        final String cleaned = normalized.replaceAll("[^a-zA-Z0-9 _]", "");
        ;
        final String capitalize = StringUtils.capitalize(cleaned);
        final String withoutSpace = capitalize.replace(" ", "");

        if (StringUtils.isBlank(withoutSpace)) return String.format("Column%s", i);

        return withoutSpace;
    }
}
