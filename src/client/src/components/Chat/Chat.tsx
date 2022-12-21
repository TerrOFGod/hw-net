import React, {FC, useRef, useState} from 'react';
import {Message} from "../../models/message";
import {IChatProps} from "./IChatProps";
import "./Chat.tsx.css";
import SendIcon from '@mui/icons-material/Send';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import {File} from "../../models/file";
import {Button, TextField} from "@mui/material";

const Chat : FC<IChatProps> = ({sender, fileUrl: baseUrl, hub}) => {
    const [userMessage, setUserMessage] = useState('');
    const [messageSending, setMessageSending] = useState(false);
    const [selectedFile, setSelectedFile] = useState<File>();
    const [key, setKey] = useState<string | null>(null);
    const inputRef = useRef<HTMLInputElement>(null);
    const fileInputRef = useRef<any>(null);

    function handlePick() {
        fileInputRef.current.click();
    }

    const  handleChange = (event: any) => {
        console.log(event.target.files);
        setSelectedFile(event.target.files[0]);
    };

    const setToken = () => {
        //token logic goes here
        console.log("setting token");
    };

    React.useEffect(() => {
        console.log(key);
    }, [key]);

    const handleUpload : () => Promise<string | null> = async () => {
        if(selectedFile){
            try {

                let formData = new FormData();
                // @ts-ignore
                formData.set('file', selectedFile!);

                const response = await fetch(baseUrl + "/file", {
                    method: 'POST',
                    body: formData
                });

                const data = await response.json();
                let fkey = data.key;

                console.log("Response: " + fkey);

                console.log("Before setting key " + key);

                setKey(data.key);

                console.log("After setting key " + key);

                const test = await fetch(process.env.REACT_APP_FILE_SERVER_URL + "/file/" + data.key,{
                    method: 'GET',
                    headers: new Headers({ responseType: 'blob' })
                })

                console.log(test);
                // @ts-ignore
                console.log(test.headers['content-type']);

                return fkey;

            }catch(response) {
                console.log(response);
                return null;
            }
            finally {
                setSelectedFile(undefined);
            };
            return null;
        }
        return null;
    };

    async function sendMessage() {
        const inputMessage = userMessage.trim();
        if (inputMessage.length < 3) {
            alert('Min message length is 3');
            return;
        }
        await handleUpload().then(async (fkey) =>{
            console.log(`After handle upload: ${key}`)
            console.log("Prosto: " + key);
            const sendMessageItem: Message = {sender: sender, content: userMessage, fileKey: fkey == '' ? null : fkey};
            console.log(sendMessageItem);
            await hub.invoke('SendMessage', sendMessageItem)
                .catch(err => {
                    console.log(err);
                })
            setKey(null);
            setUserMessage('');
        });
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
            <TextField variant="outlined"
                       label="Message Input"
                       className={'col-10'}
                       value={userMessage}
                       onChange={e => setUserMessage(e.currentTarget.value)}
                       onKeyDown={onInputKeyDown}
                       autoFocus={true}
                       disabled={messageSending}
                       ref={inputRef}/>
            <div className={'upload-file-area'}>
                <Button variant="contained" component="label" endIcon={<AttachFileIcon/>} onClick={handlePick}>
                    Upload
                    <input hidden accept="image/*,text/*"
                           ref={fileInputRef}
                           onChange={handleChange}
                           type="file"/>
                </Button>
                {selectedFile && (
                    <div className={"file-name-container"}>
                        {selectedFile.name}
                    </div>
                )}
            </div>

            <Button endIcon={<SendIcon/>}
                     disabled={messageSending}
                     onClick={onSendMessageButtonClick}>
                Send
            </Button>
        </div>
    );
}

export default Chat;