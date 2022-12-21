FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ProSiRMQ.Infrastructure/ProSiRMQ.Infrastructure.csproj", "ProSiRMQ.Infrastructure/"]
COPY ["ProSiRMQ.DB/ProSiRMQ.DB.csproj", "ProSiRMQ.DB/"]
COPY ["ProSiRMQ.Message.Listener/ProSiRMQ.Message.Listener.csproj", "ProSiRMQ.Message.Listener/"]
RUN dotnet restore "ProSiRMQ.Message.Listener/ProSiRMQ.Message.Listener.csproj"
COPY . .
WORKDIR "/src/ProSiRMQ.Message.Listener"
RUN dotnet build "ProSiRMQ.Message.Listener.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ProSiRMQ.Message.Listener.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ProSiRMQ.Message.Listener.dll"]
