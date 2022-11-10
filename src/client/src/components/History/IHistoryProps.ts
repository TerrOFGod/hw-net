import {Message} from "../../models/message";

export interface IHistoryProps{
    messages: Message[]
    message: Message
}