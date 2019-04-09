package domain;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import domain.handler.LottoInputHandler;
import domain.handler.LottoOutputHandler;

public class LottoEvent {

    public static final int LOTTO_PRICE = 1000;

    private final LottoInputHandler lottoInputHandler;
    private final LottoFactory lottoFactory;
    private final List<Lotto> purchasedLotto;

    public LottoEvent() {
        lottoInputHandler = new LottoInputHandler();
        lottoFactory = new LottoFactory();
        purchasedLotto = new ArrayList<>();
    }

    private void purchaseLotto() {
        int purchaseAmount = lottoInputHandler.getPurchaseAmount();
        purchaseLottoWithPurchaseAmount(purchaseAmount);
    }

    private void purchaseLottoWithPurchaseAmount(int purchaseAmount) {
        int numOfLottoToCreate = purchaseAmount / LOTTO_PRICE;
        while (numOfLottoToCreate > 0) {
            Lotto lotto = lottoFactory.getInstance();
            purchasedLotto.add(lotto);
            numOfLottoToCreate--;
        }
    }

    private WinningLotto createWinningLotto() {
        List<Integer> winningNumList = convertIntArrayToList(lottoInputHandler.getWinningNums());
        int bonusNum = lottoInputHandler.getBonusNum();

        return createWinningLottoWithParams(winningNumList, bonusNum);
    }

    private WinningLotto createWinningLottoWithParams(List<Integer> winningNumList, int bonusNum) {
        Lotto winner = lottoFactory.getInstance(winningNumList);
        return new WinningLotto(winner, bonusNum);
    }

    private void showResult(WinningLotto winningLotto, int purchaseAmount) {
        Map<Rank, Integer> results = doLottoEvent(winningLotto);
        LottoOutputHandler lottoOutputHandler = new LottoOutputHandler(results);

        lottoOutputHandler.showLottoEventResult();
        lottoOutputHandler.showProfitRate(purchaseAmount);
    }

    private Map<Rank, Integer> doLottoEvent(WinningLotto winningLotto) {
        return new LottoEventJudge(winningLotto).judgeLottoEvent(purchasedLotto);
    }

    private List<Integer> convertIntArrayToList(int[] winningNums) {
        return Arrays.stream(winningNums).boxed().collect(Collectors.toList());
    }
}
