import React, {useEffect, useState} from 'react';
import './App.css';
import {useEffectOnce} from "../../hooks/useEffectOnce";
import Chat from "../../components/Chat/Chat";
import History from "../../components/History/History";
import {HubConnection, HubConnectionBuilder} from "@microsoft/signalr";
import {Message} from "../../models/message";
import axios from "axios";

const App = () => {
    const messageUrl = process.env.REACT_APP_SERVER_URL/*"https://localhost:7039"*/ ;
    if (!messageUrl) {
        throw new Error('Server url is not provided');
    }

    const fileUrl = process.env.REACT_APP_FILE_SERVER_URL/*"https://localhost:7039"*/ ;
    if (!fileUrl) {
        throw new Error('Server url is not provided');
    }

    const metadataUrl = process.env.REACT_APP_METADATA_SERVER_URL/*"https://localhost:7039"*/ ;
    if (!metadataUrl) {
        throw new Error('Server url is not provided');
    }

    const [username, setUsername] = useState('');
    const [hubConnection, setHubConnection] = useState<HubConnection>();
    const [messages, setMessages] = useState<Message[]>([]);
    const [fileKey, setFileKey] = useState('');
    const [msg] = useState<Message>({sender: "",content: "",fileKey: ""});

    useEffectOnce(() => {
        axios.get<Message[]>(messageUrl + '/messages/15')
            .then(value => {
                setMessages(value.data);
            });
    });

    const createHubConnection = () =>{
        const connection = new HubConnectionBuilder().withUrl(messageUrl + "/chat").build();
        try{
            connection.start().then(() => {
                connection.on('PublishMessage', (message: Message) => {
                    setMessages((ms) => [...ms, message]);
                });
                connection.on('ReceiveFileUploaded', (key: string) => {
                    console.log("Receive file: " + key);
                    setFileKey(key);
                });
            }).catch(function () {});
        }
        catch(e){
            console.log(e);
        }
        return connection;
    }



    useEffect(() => {
        setHubConnection( createHubConnection());
        /*console.log(messages);*/
    }, [ ]);

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
                <Chat sender={username} fileUrl={fileUrl} hub={hubConnection} metadataUrl={metadataUrl} fileKey={fileKey}/>
            </div>
        </div>
    );
};

export default App;
