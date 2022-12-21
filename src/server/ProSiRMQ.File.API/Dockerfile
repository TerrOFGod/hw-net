FROM mcr.microsoft.com/dotnet/aspnet:6.0 AS base
WORKDIR /app
EXPOSE 80
EXPOSE 443

FROM mcr.microsoft.com/dotnet/sdk:6.0 AS build
WORKDIR /src
COPY ["ProSiRMQ.File.API/ProSiRMQ.File.API.csproj", "ProSiRMQ.File.API/"]
COPY ["ProSiRMQ.Infrastructure/ProSiRMQ.Infrastructure.csproj", "ProSiRMQ.Infrastructure/"]
RUN dotnet restore "ProSiRMQ.File.API/ProSiRMQ.File.API.csproj"
COPY . .
WORKDIR "/src/ProSiRMQ.File.API"
RUN dotnet build "ProSiRMQ.File.API.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "ProSiRMQ.File.API.csproj" -c Release -o /app/publish

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "ProSiRMQ.File.API.dll"]
