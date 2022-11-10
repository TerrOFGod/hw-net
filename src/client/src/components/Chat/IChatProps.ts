import { HubConnection } from "@microsoft/signalr";

export interface IChatProps{
    sender: string
    hub: HubConnection | undefined
}