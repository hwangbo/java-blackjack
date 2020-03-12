package domains.result;

import domains.card.Card;
import domains.card.Symbol;
import domains.card.Type;
import domains.user.Dealer;
import domains.user.Hands;
import domains.user.Player;
import domains.user.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultTest {
    private GameResult gameResult;

    @BeforeEach
    void setUp() {
        gameResult = new GameResult();
    }

    @DisplayName("참가자들과 딜러의 게임 결과를 계산하여 승패를 반환")
    @ParameterizedTest
    @MethodSource("gameData")
    void getWinOrLose_GivenPlayers_WinAndDraw(Players players, Dealer dealer) {
        gameResult.create(players, dealer);

        Iterator<Player> iterator = players.iterator();
        Player ddoring = iterator.next();
        Player smallBear = iterator.next();

        assertThat(gameResult.getWinOrLose(ddoring)).isEqualTo(WinOrLose.WIN);
        assertThat(gameResult.getWinOrLose(smallBear)).isEqualTo(WinOrLose.DRAW);
    }

    @DisplayName("딜러의 게임 결과를 확인")
    @ParameterizedTest
    @MethodSource("gameData")
    void calculateDealerResult__ReturnWin(Players players, Dealer dealer) {
        gameResult.create(players, dealer);

        assertThat(gameResult.calculateDealerResult()).isEqualTo("0승 1무 1패");
    }

    static Stream<Arguments> gameData() {
        Card ace = new Card(Symbol.ACE, Type.CLUB);
        Card king = new Card(Symbol.KING, Type.HEART);
        Card four = new Card(Symbol.FOUR, Type.DIAMOND);

        Dealer dealer = new Dealer(new Hands(Arrays.asList(ace, four)));

        Player ddoring = new Player("또링", new Hands(Arrays.asList(ace, king)));
        Player smallBear = new Player("작은곰", new Hands(Arrays.asList(ace, four)));
        Players players = new Players(new ArrayList<>(Arrays.asList(ddoring, smallBear)));

        return Stream.of(
                Arguments.of(players, dealer)
        );
    }
}
