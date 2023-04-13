import { HubConnection } from "@microsoft/signalr";

export interface IChatProps{
    sender: string
    fileUrl: string
    metadataUrl: string
    fileKey: string
    hub: HubConnection
}