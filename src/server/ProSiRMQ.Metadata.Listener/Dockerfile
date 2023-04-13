FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ProSiRMQ.Infrastructure/ProSiRMQ.Infrastructure.csproj", "ProSiRMQ.Infrastructure/"]
COPY ["ProSiRMQ.Metadata.Listener/ProSiRMQ.Metadata.Listener.csproj", "ProSiRMQ.Metadata.Listener/"]
RUN dotnet restore "ProSiRMQ.Metadata.API/ProSiRMQ.Metadata.Listener.csproj"
COPY . .
WORKDIR "/src/ProSiRMQ.Metadata.Listener"
RUN dotnet build "ProSiRMQ.Metadata.Listener.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ProSiRMQ.Metadata.Listener.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ProSiRMQ.Metadata.Listener.dll"]