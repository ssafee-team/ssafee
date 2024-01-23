<template lang="">
      <div class="menu-categories">
      <div
        v-for="(category, index) in categories"
        :key="index"
        @click="selectCategory(index)"
        :class="{ 'selected': selectedCategory === index }"
      >
        {{ category }}
      </div>
      </div>
      <div class="menu-items">
      <div v-for="(drink, index) in selectedDrinks" :key="index" class="drink-item">
        <img :src="drink.photo" :alt="drink.name" />
        <div>{{ drink.name }}</div>
        <div>{{ drink.price }}</div>
      </div>
    </div>
</template>
<script>
export default {
  data() {
    return {
      categories: ["Category 1", "Category 2", "Category 3", "Category 4", "Category 5", "Category 6", "Category 7", "Category 8", "Category 9", "Category 10"],
      drinks: {
        0: [
          { name: "Drink 1", photo: "blueberry.jpg", price: "2,000원" },
          { name: "Drink 2", photo: "blueberry.jpg", price: "2,000원" },
          { name: "Drink 2", photo: "blueberry.jpg", price: "2,000원" },
          { name: "Drink 2", photo: "blueberry.jpg", price: "2,000원" },
          { name: "Drink 2", photo: "blueberry.jpg", price: "2,000원" },
          // ... add more drinks for category 1
        ],
        1: [
          { name: "Drink 3", photo: "blueberry.jpg", price: "2,000원" },
          { name: "Drink 4", photo: "blueberry.jpg", price: "2,000원" },
          // ... add more drinks for category 2
        ],
        // ... add drinks for other categories
      },
      selectedCategory: 0,
    };
  },
  computed: {
    selectedDrinks() {
      return this.drinks[this.selectedCategory] || [];
    },
  },
  methods: {
    selectCategory(index) {
      this.selectedCategory = index;
    },
    getImagePath(photo) {
      return require(`@/assets/img/${photo}`);
    },
  },
  
};
</script>
<style>
.menu-categories {
  display: flex;
  flex-wrap: wrap;
}

.menu-categories > div {
  flex: 1 0 20%; /* 확장 가능, 축소 불가능, 최대 너비 20% */
  cursor: pointer;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;

  
}

.menu-categories > div.selected {
  background-color: #ddd;
}

.menu-items {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin-top: 20px;
}


.drink-item {
  text-align: center;
  margin: 10px;
}

.drink-item img {
  border: 1px solid #ccc;
  width: 200px;
  height: 150px;
}
</style>
