namespace ProSiRMQ.Infrastructure.Interfaces;

public interface ISettings
{
#pragma warning disable CA2252
    static abstract string SectionName { get; }
#pragma warning restore CA2252
}