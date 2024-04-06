package org.example.com;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BasketSplitter {
    private final Map<String, List<String>> basketsConfig = new HashMap<>();
    public BasketSplitter(String absolutePathToConfigFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            var products = objectMapper.readValue(new File(absolutePathToConfigFile), Map.class);
            basketsConfig.putAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Map<String, List<String>> split(List<String> items) {

        // count entries of each delivery type
        Map<String, Integer> counter = new HashMap<>();
        for (var item: items) {
            var delivery = basketsConfig.get(item);
            for (var d: delivery) {
                var count = counter.getOrDefault(d, 0);
                counter.put(d, count+1);
            }
        }

        TreeSet<DeliveryCount> deliveryCounts = new TreeSet<>();

        for (var entry: counter.entrySet()) {
            deliveryCounts.add(new DeliveryCount(entry.getKey(), entry.getValue()));
        }

        Map<String, List<String>> resultBasket = new HashMap<>();

        // greedy algorithm to get maximum numbers of orders in one basket
        for (var item: items) {
            var delivery = basketsConfig.get(item);
            var first = true;
            for (var dc: deliveryCounts) {
                if (delivery.contains(dc.deliveryType)) {
                    if (first) {
                        first = false;
                        var basket = resultBasket.getOrDefault(dc.deliveryType, new LinkedList<>());
                        basket.add(item);
                        resultBasket.put(dc.deliveryType, basket);
                    }
                    dc.decreaseCount();
                }
            }
        }


        List<Map.Entry<String, List<String>>> flatten = new ArrayList<>(resultBasket.entrySet().stream().toList());
        flatten.remove(flatten.size()-1);
        flatten.sort(Comparator.comparingInt(o -> o.getValue().size()));

        Set<String> removed = new HashSet<>();

        // reducing number of baskets in the result of the greedy algorithm
        for(int i=flatten.size(); i>0; i--) {
            Map<String, String> replacements = new HashMap<>();
            var toBeReplaced = flatten.get(i-1).getValue().size();
            for(var item: flatten.get(i-1).getValue()) {
                var delivery = basketsConfig.get(item);
                boolean used = false;
                for(var d: delivery) {
                    if (resultBasket.containsKey(d) && !resultBasket.get(d).contains(item) && !removed.contains(d) && !used) {
                        toBeReplaced--;
                        replacements.put(item, d);
                        used = true;
                    }
                }
            }
            if (toBeReplaced == 0) {
                var deliveryType = flatten.get(i-1).getKey();
                removed.add(deliveryType);
                for(var entry: replacements.entrySet()) {
                    resultBasket.get(entry.getValue()).add(entry.getKey());
                }
                resultBasket.remove(deliveryType);
            }
        }

        return resultBasket;
    }

    // helper class to be used in TreeSet
    private class DeliveryCount implements Comparable<DeliveryCount>{
        private int count;
        private final String deliveryType;


        private DeliveryCount(String deliveryType, int count) {
            this.deliveryType = deliveryType;
            this.count = count;
        }


        public int getCount() {
            return count;
        }

        public void decreaseCount() {
            count++;
        }


        @Override
        public int compareTo(DeliveryCount other) {
            var countComparison = (-1) * (this.getCount()- other.getCount());
            if (countComparison == 0) {
                return this.deliveryType.compareTo(other.deliveryType);
            }
            return countComparison;
        }

        @Override
        public String toString() {
            return "DeliveryCount{" +
                    "count=" + count +
                    ", deliveryType='" + deliveryType + '\'' +
                    '}';
        }
    }
}