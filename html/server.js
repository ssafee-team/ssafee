const http = require('http');
const socketIo = require('socket.io');

const server = http.createServer((req, res) => {
    res.end('WebSocket Server is running');
});

const io = socketIo(server);

io.on('connection', (socket) => {
    console.log('A user connected');

    socket.on('sendAlert', () => {
        // 사용자 A로부터 메시지를 받고, 모든 클라이언트(여기서는 사용자 B)에게 이를 전송합니다.
        io.emit('receiveAlert');
    });
});

const PORT = 3000;
server.listen(PORT, () => console.log(`Server listening on port ${PORT}`));
