import React, { useState } from "react";
import {
  Divider,
  Grid,
  Paper,
  Typography,
  Avatar,
  TextField,
  Chip,
} from "@material-ui/core";
import InputAdornment from "@material-ui/core/InputAdornment";
import SendIcon from "@material-ui/icons/Send";
import ChatBubble from "./ChatBubble";
import { GetMessagesStreamResponse } from "../proto/chat";

const style: { [key: string]: React.CSSProperties } = {
  container: {
    height: "100vh",
    padding: "0rem",
    width: "85vw",
    alignSelf: "center"
  },
  paper: {
    padding: "30px",
    height: "100%",
    display: "flex",
    flexDirection: "column",
    alignItems: "start",
    backgroundColor: "lightslategrey",
  },
  avatar: {
    margin: "20px",
  },
};

interface Props {
  username: string;
  messages: Array<GetMessagesStreamResponse>;
  onMessageSubmit: (msg: string, onSuccess: () => void) => void;
  userJoined: boolean
}

const Chat: React.FC<Props> = (props) => {
  const [msg, setMsg] = useState("");
  const { username, messages, onMessageSubmit, userJoined } = props;

  const handleSendMessage = (e: React.FormEvent<HTMLFormElement>) => {
    console.log("called");
    e.preventDefault();
    if (!msg) return;
    console.log("here ", msg);
    onMessageSubmit(msg, () => setMsg(""));
  };

  return (
    userJoined ? 
      <form onSubmit={handleSendMessage}>
      <Grid container style={style.container} spacing={3}>
        
        <Grid item xs={12}>
          <Paper style={style.paper}>
            <div
              style={{
                height: "100%",
                width: "100%",
                backgroundColor: "aliceblue",
                display: "flex",
                flexDirection: "column",

              }}
            >
              {/* {name banner} */}
              <div
                style={{
                  height: "10%",
                  display: "flex",
                  alignItems: "center",
                }}
              >
                <Avatar style={style.avatar} />
                <Grid
                  container
                  direction="column"
                  justify="center"
                  alignItems="center"
                >
                  <Typography variant="button">{username}</Typography>
                  <Chip
                    color="primary"
                    size="small"
                    style={{ width: "70px" }}
                    label="online"
                  />
                </Grid>
              </div>
              <Divider />
              <div style={{ height: "752px", overflowY: "auto" }}>
                {messages.map((msg, i) => (
                  <ChatBubble
                    key={i}
                    message={msg}
                  />
                ))}
              </div>
              <Divider />
              <div
                style={{ width: "100%", alignItems: "center", padding: "10px" }}
              >
                <TextField
                  fullWidth
                  variant="outlined"
                  placeholder="Start Typing..."
                  value={msg}
                  onChange={(e) => setMsg(e.target.value)}
                  InputProps={{
                    endAdornment: (
                      <InputAdornment position="end">
                        <SendIcon />
                      </InputAdornment>
                    ),
                  }}
                />
              </div>
            </div>
          </Paper>
        </Grid>
      </Grid>
    </form>
    : <div>Waiting for user</div>
    
    
  );
};

export default Chat;