import React, {useEffect, useState} from 'react';
import './App.css';
import {useEffectOnce} from "../../hooks/useEffectOnce";
import Chat from "../../components/Chat/Chat";
import History from "../../components/History/History";
import {HubConnection, HubConnectionBuilder} from "@microsoft/signalr";
import {Message} from "../../models/message";
import {Forum} from "../../services/forum";
import axios from "axios";

const App = () => {
    const url = process.env.REACT_APP_SERVER_URL/*"https://localhost:7039"*/ ;
    if (!url) {
        throw new Error('Server url is not provided');
    }
    const [chat,] = useState(new Forum(`${url}`));
    const [username, setUsername] = useState('');
    const [hubConnection, setHubConnection] = useState<HubConnection>();
    const [messages, setMessages] = useState<Message[]>([]);
    const [msg, setMsg] = useState<Message>({sender: "",content: ""});

    useEffectOnce(() => {
        axios.get<Message[]>(url + '/history/15')
            .then(value => {
                setMessages(value.data);
            });
    });

    const createHubConnection = () =>{
        const connection = new HubConnectionBuilder().withUrl(url + "/chat").build();
        try{
            connection.start().then(() => {
                connection.on('PublishMessage', (message: Message) => {
                    setMessages((ms) => [...ms, message]);
                });
            }).catch(function (e) {});
        }
        catch(e){
            console.log(e);
        }
        return connection;
    }



    useEffect(() => {
        setHubConnection( createHubConnection());
        console.log(messages);
    }, [ messages]);

    const minNameLength = 5;
    useEffectOnce(() => {
        let name: string | null = null;
        let message = `Login (Minimum length ${minNameLength})`;
        while (!name || name.length < minNameLength) {
            name = window.prompt(message);
            if (name)
                name = name.trim();

            message = `Minimum length - ${minNameLength}. Try again!`
        }

        setUsername(name!);
    });

    if (!hubConnection)
        return <div>Loading...</div>

    return (
        <div className={'mse-auto w-80 container-lg h-100'}>
            <div className={'h-100 chat-page'}>
                <History messages={messages} message={msg}/>
                <Chat sender={username} hub={hubConnection}/>
            </div>
        </div>
    );
};

export default App;
