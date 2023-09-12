import { useEffect, useState } from 'react';
import './App.css';
import { GetMessagesStreamResponse, SendMessageRequest } from './proto/chat'
import { ChatServiceClient } from './proto/chat.client'
import Greeting from './components/Greetings';
import Chat from './components/Chat';
import { GrpcWebFetchTransport, GrpcWebOptions } from "@protobuf-ts/grpcweb-transport";
import { Empty } from './proto/google/protobuf/empty';


let transport = new GrpcWebFetchTransport({
  baseUrl: "http://localhost:5000"
  
});

const client = new ChatServiceClient(transport)

function App() {
  const [username, setUsername] = useState<string>("")
  const [messages, setMessages] = useState<Array<GetMessagesStreamResponse>>([])
  const [userJoined, setUserJoined] = useState(false)

  const receiveMessages = async () => {
    if (!username) return username
    let streams = client!.getInfo({username: username}, {});
    for await (let message of streams.responses) {
        console.log(message.eventText);
        if (message.eventText === "UserJoined") {
            setUserJoined(true);
        }
    }
  }

  const connect = async () => {
    if (!username) return 
    return await client.join({username: username}, {});
  }

  const receive = async () => {
    if (!username) return
    const stream = client!.getMessagesStream({username: username}, {});

    for await (let message of stream.responses) {
        setMessages(m => [...m, message])
    } 
  }

  useEffect(() => {
    if (userJoined) {
        receive();
    }
  }, [userJoined]);

  useEffect(() => {
    connect().then(_ => receiveMessages());
  }, [username])

  const handleUserSubmit = (name: string) => {
      console.log(client)
      if(!name) return
      console.log(name)
      setUsername(name)
  }

  const handleMessageSubmit = (msg: string, onSuccess: () => void) => {
    console.log(client)
    if (!username || !msg.trim()) return
    const messageRequest: SendMessageRequest = {message: msg, username: username}
    const call = client.sendMessage(messageRequest, {})
    call.response.then((resp) => {
      onSuccess()
    },
    (err) => {
      console.error(err)
    })
  }

  return (
    <div className="App">
      <div className="App-container">
        {!username ? 
          <Greeting onUsernameEnter={handleUserSubmit}/> :
          <Chat username = {username} messages={messages} userJoined={userJoined} onMessageSubmit={handleMessageSubmit} />}
      </div>
    </div>
  );
}

export default App;
