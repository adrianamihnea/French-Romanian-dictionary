const WebSocket = require('ws');
const axios = require('axios');

const wss = new WebSocket.Server({ port: 8080 });

wss.on('connection', async (ws) => {
  console.log('Client connected');

  ws.on('message', async (message) => {
    const data = JSON.parse(message);
    if (data.type === 'randomWordRequest') {
      try {
        const response = await axios.get('http://localhost:3000/words/randomWord');
        const word = response.data;
        ws.send(word); // Send the random word to the client
      } catch (error) {
        console.error('Error fetching random word:', error.message);
      }
    }
  });

  ws.on('close', () => {
    console.log('Client disconnected');
  });
});
