package com.bookseller.dto;

public enum OrderPromotion {

        PROMO10OFF("PROMO10OFF", 10), PROMO20OFF("PROMO20OFF", 20),PROMO30OFF("PROMO30OFF", 30);

        private final String key;
        private final Integer value;

        OrderPromotion(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        public Integer getValue() {
            return value;
        }

}
