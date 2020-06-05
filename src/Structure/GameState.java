package Structure;

public enum GameState {
    hostSetupFail,
    waitingForClient,
    tryConnectToHost,
    connected,
    disconnected,
    active,
    whiteWon,
    blackWon,
    draw
}
