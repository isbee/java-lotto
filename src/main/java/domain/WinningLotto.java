package domain;

/**
 * 당첨 번호를 담당하는 객체
 */
public class WinningLotto {
    private final Lotto lotto;
    private final int bonusNo;

    public WinningLotto(Lotto lotto, int bonusNo) {
        this.lotto = lotto;
        this.bonusNo = bonusNo;
    }

    public Rank match(Lotto userLotto) {
        int matchingNum = lotto.countMatchingLottoNum(userLotto);
        boolean doesBonusMatched = userLotto.doesLottoContains(bonusNo);

        return Rank.valueOf(matchingNum, doesBonusMatched);
    }
}
