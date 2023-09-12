import React from "react";
import Grid from "@material-ui/core/Grid";
import "./style.css";
import { GetMessagesStreamResponse } from "../proto/chat";

interface Props {
  message: GetMessagesStreamResponse;
}

const ChatBubble: React.FC<Props> = ({ message }) => {
  const { message: text, username} = message;

  return (
    <Grid
      container
      item
      style={{
        width: "100%",
        justifyContent: "flex-end"
      }}
    >
      <blockquote
        className="speech-bubble"
        style={{ backgroundColor: "#bad4ff"}}
      >
        <p>{text}</p>
        {<cite>{username}</cite>}
      </blockquote>
    </Grid>
  );
};

export default ChatBubble;