import React, {FC, useRef, useState} from 'react';
import {Message} from "../../models/message";
import {IChatProps} from "./IChatProps";
import "./Chat.tsx.css"

const Chat : FC<IChatProps> = ({sender, hub}) => {
    const [userMessage, setUserMessage] = useState('');
    const [messageSending, setMessageSending] = useState(false);
    const inputRef = useRef<HTMLInputElement>(null);

    async function sendMessage() {
        const inputMessage = userMessage.trim();
        if (inputMessage.length < 3) {
            alert('Min message length is 3');
            return;
        }
        const message: Message = {sender: sender, content: userMessage};
        setMessageSending(true);
        try {
            if(hub){
                await hub.invoke('SendMessage', message)
                    .catch(err => {
                        console.log(err);
                    })
            }
            setUserMessage('');
        } finally {
            setMessageSending(false);
        }
    }

    const onSendMessageButtonClick = async () => {
        try {
            await sendMessage();
        } finally {
            inputRef.current?.focus();
        }
    };

    const onInputKeyDown = async (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            try {
                await sendMessage();
            } finally {
                inputRef.current?.focus();
            }
        }
    }

    return (
        <div className={'d-flex m-0'}>
            <input className={'col-10 m-0 p-0 b-0 form-control bg-white my-2 h-45p rounded-l'}
                    placeholder={'Введите сообщение другим участникам'}
                    value={userMessage}
                    onChange={e => setUserMessage(e.currentTarget.value)}
                    onKeyDown={onInputKeyDown}
                    autoFocus={true}
                    disabled={messageSending}
                    ref={inputRef}/>
            <button className={'col-2 m-0 p-0 b-0 btn btn-success mb-1 h-45p rounded-r'}
                    disabled={messageSending}
                    onClick={onSendMessageButtonClick}>
                Отправить
            </button>  
        </div>
    );
}

export default Chat;