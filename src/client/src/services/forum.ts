import {Message} from "../models/message";

const parseMessage = (obj: any): Message => {
    const content = obj.content;
    const sender = obj.sender;
    if (!(content && sender)) {
        throw new Error('Could not get message from response. Message and username not provided');
    }

    if (typeof content !== 'string') {
        throw new Error(`Message must be string. Given: ${content}`)
    }

    if (typeof sender !== 'string') {
        throw new Error(`Username must be string. Given: ${sender}`)
    }

    return {
        sender, content
    }
}

// @ts-ignore
export class Forum{
    constructor(readonly url: string) {}



    async getHistory(size: number): Promise<Message[]> {
        const requestHeaders: HeadersInit = new Headers();
        requestHeaders.set('Access-Control-Allow-Origin', '*');
        const response = await fetch(`${this.url}/history/${size}`, {
            method: 'GET',
            mode: 'cors',
            headers: requestHeaders,
            credentials: 'same-origin'
        })
        console.log(response);

        if (!response.ok) {
            throw new Error(`Could not get response from server. Invalid response status code: ${response.status} ${response.statusText}`)
        }
        const json: Array<any> = await response.json();
        console.log(json);
        try {
            return json.map(parseMessage)
        } catch (e: any) {
            throw new Error('Server responded with invalid json. Could not parse', e)
        }
    }

}