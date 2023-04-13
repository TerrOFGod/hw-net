import React, {FC, useState} from 'react';
import {IFormatProps} from "./IFormatProps";
import {Box, TextField} from "@mui/material";

const Format : FC<IFormatProps> = ({format, onChangeValue}) => {
    const [fileName, setFileName] = useState('');
    const [otherFormat, setOtherFormat] = useState('');
    const [duration, setDuration] = useState(0);
    const [author, setAuthor] = useState('');

    let form;
    switch (format){
        case "Image":
            form =  <div></div>
            break;
        case "Audio":
            form =  <Box display="flex" flexDirection="column" justifyContent={"center"} padding={1} gap={2}>
                <TextField
                    required
                    id="outlined-required"
                    type="number"
                    name={"videoDuration"}
                    label="Duration"
                    value={duration}
                    onChange={(e) => {
                        setDuration(Number(e.target.value));
                        onChangeValue(e.target);
                    }}
                />
                <TextField
                    required
                    id="outlined-required"
                    name={"audioAuthor"}
                    label="Author"
                    value={author}
                    onChange={(e) => {
                        setAuthor(e.target.value);
                        onChangeValue(e.target);
                    }}
                />
            </Box>
            break;
        case "Video":
            form =  <Box display="flex" flexDirection="column" justifyContent={"center"} padding={1} gap={2}>
                <TextField
                    required
                    id="outlined-required"
                    type="number"
                    name={"videoDuration"}
                    label="Duration"
                    value={duration}
                    onChange={(e) => {
                        setDuration(Number(e.target.value));
                        onChangeValue(e.target);
                    }}
                />
            </Box>
            break;
        case "Other":
            form =  <Box display="flex" flexDirection="column" justifyContent={"center"} padding={1} gap={2}>
                <TextField
                    required
                    id="outlined-required"
                    name={"otherFileFormat"}
                    label="Format"
                    value={otherFormat}
                    onChange={(e) => {
                        setOtherFormat(e.target.value);
                        onChangeValue(e.target);
                    }}
                />
            </Box>
            break;
        default:
            form =  <div>
                Select file format
            </div>
            break;
    };

    return (
        <div>
            {form}
        </div>
    );
};

export default Format;