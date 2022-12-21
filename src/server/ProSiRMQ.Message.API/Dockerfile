FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ProSiRMQ.Infrastructure/ProSiRMQ.Infrastructure.csproj", "ProSiRMQ.Infrastructure/"]
COPY ["ProSiRMQ.DB/ProSiRMQ.DB.csproj", "ProSiRMQ.DB/"]
COPY ["ProSiRMQ.Message.API/ProSiRMQ.Message.API.csproj", "ProSiRMQ.Message.API/"]
RUN dotnet restore "ProSiRMQ.Message.API/ProSiRMQ.Message.API.csproj"
COPY . .
WORKDIR "/src/ProSiRMQ.Message.API"
RUN dotnet build "ProSiRMQ.Message.API.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ProSiRMQ.Message.API.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ProSiRMQ.Message.API.dll"]
