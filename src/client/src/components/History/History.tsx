import React, {FC} from 'react';
import {Message} from "../../models/message";
import "./History.tsx.css";
import {IHistoryProps} from "./IHistoryProps";

const History  : FC<IHistoryProps> = ({messages, message}) => {
    var t = 0;

    function createMessageRecord(message: Message, i: number) {
        const name = (<b>
            {message.sender ?? 'Unknown'}
        </b>)

        const contents = message.content;
        t = i;

        return (<div key={i}>
            {name}: {contents}
        </div>)
    }



    return (
        <div className={'h-100 mh-30 b-history'}>
            {messages.map((m, i) => createMessageRecord(m, i))}
        </div>
    );
}

export default History;