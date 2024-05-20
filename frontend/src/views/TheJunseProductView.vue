<template>
  <div>
    <h2>전세보증상품 추천</h2>
    <JunseProductInput @submitForm="handleFormSubmit" />
  </div>
</template>

<script setup>
import JunseProductInput from '@/components/Junseproduct/JunseProductInput.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const handleFormSubmit = async (formData) => {
  try {
    const response = await fetch('/junse', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      const result = await response.json();
      console.log('API Response:', result);
      // 성공 시 메인 페이지로 이동
      router.push({ name: 'main' });
      location.reload(); // 페이지 새로고침
    } else {
      console.error('API Error:', response.statusText);
    }
  } catch (error) {
    console.error('Request Error:', error);
  }
};
</script>

<style scoped>
/* 필요한 스타일을 추가하세요 */
</style>
