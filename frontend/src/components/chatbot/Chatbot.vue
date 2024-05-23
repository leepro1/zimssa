<template>
  <div>
    <div class="chatbot-popup" v-if="isOpen">
      <div class="chatbot-header">
        <h3>Chatbot</h3>
        <button @click="togglePopup">X</button>
      </div>
      <div class="messages">
        <div
          v-for="(message, index) in messages"
          :key="index"
          :class="message.isUser ? 'user-message' : 'bot-message'"
        >
          <div class="message-bubble">
            {{ message.text }}
          </div>
        </div>
      </div>
      <div class="input-area">
        <input v-model="question" @keyup.enter="sendQuestion" placeholder="질문 입력" />
        <button @click="sendQuestion">Send</button>
      </div>
    </div>
    <button class="open-button" @click="togglePopup" v-if="!isOpen">부동산 AI Chat</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isOpen: false,
      question: "",
      messages: [],
    };
  },
  methods: {
    togglePopup() {
      this.isOpen = !this.isOpen;
    },
    async sendQuestion() {
      if (this.question.trim() === "") return;

      const lastBotMessage = this.messages.find((msg) => !msg.isUser);
      this.messages = lastBotMessage ? [lastBotMessage] : [];
      this.messages.push({ text: this.question, isUser: true });

      const currentQuestion = this.question;
      this.question = ""; 

      try {
        const response = await axios.post(
          "http://localhost:80/zimssa/api/v1/chat-gpt",
          { question: currentQuestion },
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        this.question = "";

        this.messages.push({ text: response.data, isUser: false });
      } catch (error) {
        console.error("Error getting response from the server:", error);
      }

      this.question = "";
    },
  },
};
</script>

<style scoped>
.chatbot-popup {
  position: fixed;
  bottom: 20px;
  right: 20px;
  width: 300px;
  height: 500px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  z-index: 9999;
}

.chatbot-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background: #7468b6;
  color: white;
}

.messages {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
}

.user-message {
  text-align: right;
}

.bot-message {
  text-align: left;
}

.message-bubble {
  display: inline-block;
  padding: 8px 12px;
  border-radius: 10px;
  margin-bottom: 5px;
  max-width: 80%;
  word-wrap: break-word;
}

.user-message .message-bubble {
  background-color: #e1afd1;
}

.bot-message .message-bubble {
  background-color: #ffe6e6;
}

.input-area {
  display: flex;
  padding: 10px;
  gap: 10px;
  border-top: 1px solid #ccc;
}

input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  border: none;
  background-color: #7468b6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #5d52a1;
}

.open-button {
  z-index: 9999;
  position: fixed;
  bottom: 20px;
  right: 20px;
  padding: 10px 20px;
  border: none;
  background-color: #7468b6;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

.open-button:hover {
  background-color: #5d52a1;
}
</style>
