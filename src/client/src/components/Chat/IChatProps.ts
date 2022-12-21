import { HubConnection } from "@microsoft/signalr";

export interface IChatProps{
    sender: string
    fileUrl: string
    hub: HubConnection
}