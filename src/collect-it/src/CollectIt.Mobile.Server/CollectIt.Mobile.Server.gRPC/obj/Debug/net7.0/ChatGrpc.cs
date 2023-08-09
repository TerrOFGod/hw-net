// <auto-generated>
//     Generated by the protocol buffer compiler.  DO NOT EDIT!
//     source: chat.proto
// </auto-generated>
#pragma warning disable 0414, 1591, 8981
#region Designer generated code

using grpc = global::Grpc.Core;

public static partial class Chat
{
  static readonly string __ServiceName = "Chat";

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static void __Helper_SerializeMessage(global::Google.Protobuf.IMessage message, grpc::SerializationContext context)
  {
    #if !GRPC_DISABLE_PROTOBUF_BUFFER_SERIALIZATION
    if (message is global::Google.Protobuf.IBufferMessage)
    {
      context.SetPayloadLength(message.CalculateSize());
      global::Google.Protobuf.MessageExtensions.WriteTo(message, context.GetBufferWriter());
      context.Complete();
      return;
    }
    #endif
    context.Complete(global::Google.Protobuf.MessageExtensions.ToByteArray(message));
  }

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static class __Helper_MessageCache<T>
  {
    public static readonly bool IsBufferMessage = global::System.Reflection.IntrospectionExtensions.GetTypeInfo(typeof(global::Google.Protobuf.IBufferMessage)).IsAssignableFrom(typeof(T));
  }

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static T __Helper_DeserializeMessage<T>(grpc::DeserializationContext context, global::Google.Protobuf.MessageParser<T> parser) where T : global::Google.Protobuf.IMessage<T>
  {
    #if !GRPC_DISABLE_PROTOBUF_BUFFER_SERIALIZATION
    if (__Helper_MessageCache<T>.IsBufferMessage)
    {
      return parser.ParseFrom(context.PayloadAsReadOnlySequence());
    }
    #endif
    return parser.ParseFrom(context.PayloadAsNewBuffer());
  }

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Marshaller<global::Google.Protobuf.WellKnownTypes.Empty> __Marshaller_google_protobuf_Empty = grpc::Marshallers.Create(__Helper_SerializeMessage, context => __Helper_DeserializeMessage(context, global::Google.Protobuf.WellKnownTypes.Empty.Parser));
  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Marshaller<global::SendMessageRequest> __Marshaller_SendMessageRequest = grpc::Marshallers.Create(__Helper_SerializeMessage, context => __Helper_DeserializeMessage(context, global::SendMessageRequest.Parser));
  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Marshaller<global::SendMessageResponse> __Marshaller_SendMessageResponse = grpc::Marshallers.Create(__Helper_SerializeMessage, context => __Helper_DeserializeMessage(context, global::SendMessageResponse.Parser));
  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Marshaller<global::GetMessagesStreamResponse> __Marshaller_GetMessagesStreamResponse = grpc::Marshallers.Create(__Helper_SerializeMessage, context => __Helper_DeserializeMessage(context, global::GetMessagesStreamResponse.Parser));

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Method<global::Google.Protobuf.WellKnownTypes.Empty, global::Google.Protobuf.WellKnownTypes.Empty> __Method_Join = new grpc::Method<global::Google.Protobuf.WellKnownTypes.Empty, global::Google.Protobuf.WellKnownTypes.Empty>(
      grpc::MethodType.Unary,
      __ServiceName,
      "Join",
      __Marshaller_google_protobuf_Empty,
      __Marshaller_google_protobuf_Empty);

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Method<global::SendMessageRequest, global::SendMessageResponse> __Method_SendMessage = new grpc::Method<global::SendMessageRequest, global::SendMessageResponse>(
      grpc::MethodType.Unary,
      __ServiceName,
      "SendMessage",
      __Marshaller_SendMessageRequest,
      __Marshaller_SendMessageResponse);

  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  static readonly grpc::Method<global::Google.Protobuf.WellKnownTypes.Empty, global::GetMessagesStreamResponse> __Method_GetMessagesStream = new grpc::Method<global::Google.Protobuf.WellKnownTypes.Empty, global::GetMessagesStreamResponse>(
      grpc::MethodType.ServerStreaming,
      __ServiceName,
      "GetMessagesStream",
      __Marshaller_google_protobuf_Empty,
      __Marshaller_GetMessagesStreamResponse);

  /// <summary>Service descriptor</summary>
  public static global::Google.Protobuf.Reflection.ServiceDescriptor Descriptor
  {
    get { return global::ChatReflection.Descriptor.Services[0]; }
  }

  /// <summary>Base class for server-side implementations of Chat</summary>
  [grpc::BindServiceMethod(typeof(Chat), "BindService")]
  public abstract partial class ChatBase
  {
    /// <summary>
    /// Для админа
    /// </summary>
    /// <param name="request">The request received from the client.</param>
    /// <param name="context">The context of the server-side call handler being invoked.</param>
    /// <returns>The response to send back to the client (wrapped by a task).</returns>
    [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
    public virtual global::System.Threading.Tasks.Task<global::Google.Protobuf.WellKnownTypes.Empty> Join(global::Google.Protobuf.WellKnownTypes.Empty request, grpc::ServerCallContext context)
    {
      throw new grpc::RpcException(new grpc::Status(grpc::StatusCode.Unimplemented, ""));
    }

    /// <summary>
    /// Отправить сообщение от лица пользователя
    /// </summary>
    /// <param name="request">The request received from the client.</param>
    /// <param name="context">The context of the server-side call handler being invoked.</param>
    /// <returns>The response to send back to the client (wrapped by a task).</returns>
    [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
    public virtual global::System.Threading.Tasks.Task<global::SendMessageResponse> SendMessage(global::SendMessageRequest request, grpc::ServerCallContext context)
    {
      throw new grpc::RpcException(new grpc::Status(grpc::StatusCode.Unimplemented, ""));
    }

    /// <summary>
    /// Подписка на все приходящие в чат сообщения
    /// </summary>
    /// <param name="request">The request received from the client.</param>
    /// <param name="responseStream">Used for sending responses back to the client.</param>
    /// <param name="context">The context of the server-side call handler being invoked.</param>
    /// <returns>A task indicating completion of the handler.</returns>
    [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
    public virtual global::System.Threading.Tasks.Task GetMessagesStream(global::Google.Protobuf.WellKnownTypes.Empty request, grpc::IServerStreamWriter<global::GetMessagesStreamResponse> responseStream, grpc::ServerCallContext context)
    {
      throw new grpc::RpcException(new grpc::Status(grpc::StatusCode.Unimplemented, ""));
    }

  }

  /// <summary>Creates service definition that can be registered with a server</summary>
  /// <param name="serviceImpl">An object implementing the server-side handling logic.</param>
  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  public static grpc::ServerServiceDefinition BindService(ChatBase serviceImpl)
  {
    return grpc::ServerServiceDefinition.CreateBuilder()
        .AddMethod(__Method_Join, serviceImpl.Join)
        .AddMethod(__Method_SendMessage, serviceImpl.SendMessage)
        .AddMethod(__Method_GetMessagesStream, serviceImpl.GetMessagesStream).Build();
  }

  /// <summary>Register service method with a service binder with or without implementation. Useful when customizing the service binding logic.
  /// Note: this method is part of an experimental API that can change or be removed without any prior notice.</summary>
  /// <param name="serviceBinder">Service methods will be bound by calling <c>AddMethod</c> on this object.</param>
  /// <param name="serviceImpl">An object implementing the server-side handling logic.</param>
  [global::System.CodeDom.Compiler.GeneratedCode("grpc_csharp_plugin", null)]
  public static void BindService(grpc::ServiceBinderBase serviceBinder, ChatBase serviceImpl)
  {
    serviceBinder.AddMethod(__Method_Join, serviceImpl == null ? null : new grpc::UnaryServerMethod<global::Google.Protobuf.WellKnownTypes.Empty, global::Google.Protobuf.WellKnownTypes.Empty>(serviceImpl.Join));
    serviceBinder.AddMethod(__Method_SendMessage, serviceImpl == null ? null : new grpc::UnaryServerMethod<global::SendMessageRequest, global::SendMessageResponse>(serviceImpl.SendMessage));
    serviceBinder.AddMethod(__Method_GetMessagesStream, serviceImpl == null ? null : new grpc::ServerStreamingServerMethod<global::Google.Protobuf.WellKnownTypes.Empty, global::GetMessagesStreamResponse>(serviceImpl.GetMessagesStream));
  }

}
#endregion
