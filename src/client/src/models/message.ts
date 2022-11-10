export interface Message {
    sender: string
    content: string
}

export function parseMessage(json: string): Message {
    console.log({
        json
    })
    const parsed = JSON.parse(json);
    if (!(parsed.content && parsed.sender)) {
        throw new Error('No message passed');
    }
    if (typeof parsed.content !== 'string') {
        throw new Error('Message must be a string. Given', parsed.content);
    }

    if (typeof parsed.sender !== 'string') {
        throw new Error('Username must be string. Given: ', parsed.sender);
    }

    return {
        sender: parsed.sender,
        content: parsed.content
    }
}