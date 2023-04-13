import React, {FC, useRef, useState} from 'react';
import {Message} from "../../models/message";
import {IChatProps} from "./IChatProps";
import "./Chat.tsx.css";
import SendIcon from '@mui/icons-material/Send';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import {File} from "../../models/file";
import {Button, MenuItem, Select, SelectChangeEvent, TextField} from "@mui/material";
import {Guid} from "guid-typescript";
import Format from "../Format/Format";

const Chat : FC<IChatProps> = ({sender, fileUrl: fileUrl, metadataUrl: metadataUrl, hub, fileKey}) => {
    const [userMessage, setUserMessage] = useState('');

    const [selectedFile, setSelectedFile] = useState<File>();
    const [key, setKey] = useState<string | null>(null);

    const [fileFormat, setFileFormat] = useState('Other');

    const [otherFormat, setOtherFormat] = useState('');
    const [duration, setDuration] = useState(0);
    const [author, setAuthor] = useState('');

    const [messageSending, setMessageSending] = useState(false);

    const inputRef = useRef<HTMLInputElement>(null);
    const fileInputRef = useRef<any>(null);

    function handlePick() {
        fileInputRef.current.click();
    }

    const  handleChange = (event: any) => {
        console.log(event.target.files);
        setSelectedFile(event.target.files[0]);
    };

    const handleSelectChange = (event: SelectChangeEvent) => {
        setFileFormat(event.target.value);
    };

    const handleOnChange = (val: HTMLInputElement) => {
        console.log('fileFormat' + fileFormat);

        if (val.name === 'videoDuration' || val.name === 'audioDuration') {
            setDuration(Number(val.value));
        }

        if (val.name === 'audioAuthor') {
            setAuthor(val.value);
        }

        if (val.name === 'otherFileFormat') {
            setOtherFormat(val.value);
        }
    }

    React.useEffect(() => {
        console.log(key);
    }, [key]);

    const handleUpload : () => Promise<string | null> = async () => {
        if(selectedFile){
            try {
                const requestId = Guid.raw();

                let formData = new FormData();
                // @ts-ignore
                formData.set('File', selectedFile!);
                formData.set('RequestId', requestId);

                let metadata = metadataCreate(requestId);

                let requests = [];

                requests.push(fetch(fileUrl + "/file", {
                    method: 'POST',
                    body: formData
                }));

                requests.push(fetch(metadataUrl + "/metadata", {
                    method: 'POST',
                    body: metadata,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }));

                await Promise.all(requests);
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

    const metadataCreate = (requestId : string): string => {
        let metadata;
        switch (fileFormat){
            case "Image":
                metadata = {
                    fileName: selectedFile!.name
                };
                break;
            case "Audio":
                metadata = {
                    fileName: selectedFile!.name,
                    duration: duration,
                    author: author
                };
                break;
            case "Video":
                metadata = {
                    fileName: selectedFile!.name,
                    duration: duration
                };
                break;
            case "Other":
                metadata = {
                    fileName: selectedFile!.name,
                    otherFormat: otherFormat
                };
                break;
        }

        let obj =
            {
                requestId: requestId,
                metadata: metadata
            };

        return JSON.stringify(obj);
    }

    async function sendMessage() {
        const inputMessage = userMessage.trim();
        if (inputMessage.length < 3) {
            alert('Min message length is 3');
            return;
        }
        await handleUpload().then(async () =>{
            console.log(`After handle upload: ${key}`)
            console.log("Prosto: " + key);

            const sendMessageItem: Message = {sender: sender, content: userMessage, fileKey: fileKey};
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
                       className={'col-5'}
                       value={userMessage}
                       onChange={e => setUserMessage(e.currentTarget.value)}
                       onKeyDown={onInputKeyDown}
                       autoFocus={true}
                       disabled={messageSending}
                       ref={inputRef}/>
            <div className={'upload-file-area col-6'}>
                {!selectedFile &&(
                    <Button className={'col-1'} variant="contained"
                            component="label" endIcon={<AttachFileIcon/>} onClick={handlePick}>
                        <input hidden accept="image/*,text/*"
                               ref={fileInputRef}
                               onChange={handleChange}
                               type="file"
                               multiple={false}/>
                    </Button>
                )}
                {selectedFile && (
                    <div>
                        <div className={"file-name-container"}>
                            {selectedFile.name}
                        </div>
                        <Select labelId="demo-simple-select-standard-label"
                                id="demo-simple-select-standard"
                                value={fileFormat}
                                onChange={handleSelectChange}
                                label="Format">
                            <MenuItem value={"Other"}>Other</MenuItem>
                            <MenuItem value={"Image"}>Image</MenuItem>
                            <MenuItem value={"Video"}>Video</MenuItem>
                            <MenuItem value={"Audio"}>Audio</MenuItem>
                        </Select>
                        <Format format={fileFormat} onChangeValue={handleOnChange}/>
                    </div>
                )}
            </div>

            <Button endIcon={<SendIcon/>}
                     disabled={messageSending}
                     onClick={onSendMessageButtonClick}
                    className={'col-1'}>
            </Button>
        </div>
    );
}

export default Chat;