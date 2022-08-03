/*
 * Copyright 2016-2020 chronicle.software
 *
 *       https://chronicle.software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openhft.chronicle.wire;

public enum FIX42 implements WireKey {
    Account(1),
    AdvId(2),
    AdvRefID(3),
    AdvSide(4),
    AdvTransType(5),
    AvgPx(6),
    BeginSeqNo(7),
    BeginString(8),
    BodyLength(9),
    CheckSum(10),
    ClOrdID(11),
    Commission(12),
    CommType(13),
    CumQty(14),
    Currency(15),
    EndSeqNo(16),
    ExecID(17),
    ExecInst(18),
    ExecRefID(19),
    ExecTransType(20),
    HandlInst(21),
    IDSource(22),
    IOIid(23),
    IOIOthSvc(24),
    IOIQltyInd(25),
    IOIRefID(26),
    IOIShares(27),
    IOITransType(28),
    LastCapacity(29),
    LastMkt(30),
    LastPx(31),
    LastShares(32),
    LinesOfText(33),
    MsgSeqNum(34),
    MsgType(35),
    NewSeqNo(36),
    OrderID(37),
    OrderQty(38),
    OrdStatus(39),
    OrdType(40),
    OrigClOrdID(41),
    OrigTime(42),
    PossDupFlag(43),
    Price(44),
    RefSeqNum(45),
    RelatdSym(46),
    Rule80A(47),
    SecurityID(48),
    SenderCompID(49),
    SenderSubID(50),
    SendingDate(51),
    SendingTime(52),
    Shares(53),
    Side(54),
    Symbol(55),
    TargetCompID(56),
    TargetSubID(57),
    Text(58),
    TimeInForce(59),
    TransactTime(60),
    Urgency(61),
    ValidUntilTime(62),
    SettlmntTyp(63),
    FutSettDate(64),
    SymbolSfx(65),
    ListID(66),
    ListSeqNo(67),
    TotNoOrders(68),
    ListExecInst(69),
    AllocID(70),
    AllocTransType(71),
    RefAllocID(72),
    NoOrders(73),
    AvgPrxPrecision(74),
    TradeDate(75),
    ExecBroker(76),
    OpenClose(77),
    NoAllocs(78),
    AllocAccount(79),
    AllocShares(80),
    ProcessCode(81),
    NoRpts(82),
    RptSeq(83),
    CxlQty(84),
    NoDlvyInst(85),
    DlvyInst(86),
    AllocStatus(87),
    AllocRejCode(88),
    Signature(89),
    SecureDataLen(90),
    SecureData(91),
    BrokerOfCredit(92),
    SignatureLength(93),
    EmailType(94),
    RawDataLength(95),
    RawData(96),
    PossResend(97),
    EncryptMethod(98),
    StopPx(99),
    ExDestination(100),
    CxlRejReason(102),
    OrdRejReason(103),
    IOIQualifier(104),
    WaveNo(105),
    Issuer(106),
    SecurityDesc(107),
    HeartBtInt(108),
    ClientID(109),
    MinQty(110),
    MaxFloor(111),
    TestReqID(112),
    ReportToExch(113),
    LocateReqd(114),
    OnBehalfOfCompID(115),
    OnBehalfOfSubID(116),
    QuoteID(117),
    NetMoney(118),
    SettlCurrAmt(119),
    SettlCurrency(120),
    ForexReq(121),
    OrigSendingTime(122),
    GapFillFlag(123),
    NoExecs(124),
    CxlType(125),
    ExpireTime(126),
    DKReason(127),
    DeliverToCompID(128),
    DeliverToSubID(129),
    IOINaturalFlag(130),
    QuoteReqID(131),
    BidPx(132),
    OfferPx(133),
    BidSize(134),
    OfferSize(135),
    NoMiscFees(136),
    MiscFeeAmt(137),
    MiscFeeCurr(138),
    MiscFeeType(139),
    PrevClosePx(140),
    ResetSeqNumFlag(141),
    SenderLocationID(142),
    TargetLocationID(143),
    OnBehalfOfLocationID(144),
    DeliverToLocationID(145),
    NoRelatedSym(146),
    Subject(147),
    Headline(148),
    URLLink(149),
    ExecType(150),
    LeavesQty(151),
    CashOrderQty(152),
    AllocAvgPx(153),
    AllocNetMoney(154),
    SettlCurrFxRate(155),
    SettlCurrFxRateCalc(156),
    NumDaysInterest(157),
    AccruedInterestRate(158),
    AccruedInterestAmt(159),
    SettlInstMode(160),
    AllocText(161),
    SettlInstID(162),
    SettlInstTransType(163),
    EmailThreadID(164),
    SettlInstSource(165),
    SettlLocation(166),
    SecurityType(167),
    EffectiveTime(168),
    StandInstDbType(169),
    StandInstDbName(170),
    StandInstDbID(171),
    SettlDeliveryType(172),
    SettlDepositoryCode(173),
    SettlBrkrCode(174),
    SettlInstCode(175),
    SecuritySettlAgentName(176),
    SecuritySettlAgentCode(177),
    SecuritySettlAgentAcctNum(178),
    SecuritySettlAgentAcctName(179),
    SecuritySettlAgentContactName(180),
    SecuritySettlAgentContactPhone(181),
    CashSettlAgentName(182),
    CashSettlAgentCode(183),
    CashSettlAgentAcctNum(184),
    CashSettlAgentAcctName(185),
    CashSettlAgentContactName(186),
    CashSettlAgentContactPhone(187),
    BidSpotRate(188),
    BidForwardPoints(189),
    OfferSpotRate(190),
    OfferForwardPoints(191),
    OrderQty2(192),
    FutSettDate2(193),
    LastSpotRate(194),
    LastForwardPoints(195),
    AllocLinkID(196),
    AllocLinkType(197),
    SecondaryOrderID(198),
    NoIOIQualifiers(199),
    MaturityMonthYear(200),
    PutOrCall(201),
    StrikePrice(202),
    CoveredOrUncovered(203),
    CustomerOrFirm(204),
    MaturityDay(205),
    OptAttribute(206),
    SecurityExchange(207),
    NotifyBrokerOfCredit(208),
    AllocHandlInst(209),
    MaxShow(210),
    PegDifference(211),
    XmlDataLen(212),
    XmlData(213),
    SettlInstRefID(214),
    NoRoutingIDs(215),
    RoutingType(216),
    RoutingID(217),
    SpreadToBenchmark(218),
    Benchmark(219),
    CouponRate(223),
    ContractMultiplier(231),
    MDReqID(262),
    SubscriptionRequestType(263),
    MarketDepth(264),
    MDUpdateType(265),
    AggregatedBook(266),
    NoMDEntryTypes(267),
    NoMDEntries(268),
    MDEntryType(269),
    MDEntryPx(270),
    MDEntrySize(271),
    MDEntryDate(272),
    MDEntryTime(273),
    TickDirection(274),
    MDMkt(275),
    QuoteCondition(276),
    TradeCondition(277),
    MDEntryID(278),
    MDUpdateAction(279),
    MDEntryRefID(280),
    MDReqRejReason(281),
    MDEntryOriginator(282),
    LocationID(283),
    DeskID(284),
    DeleteReason(285),
    OpenCloseSettleFlag(286),
    SellerDays(287),
    MDEntryBuyer(288),
    MDEntrySeller(289),
    MDEntryPositionNo(290),
    FinancialStatus(291),
    CorporateAction(292),
    DefBidSize(293),
    DefOfferSize(294),
    NoQuoteEntries(295),
    NoQuoteSets(296),
    QuoteAckStatus(297),
    QuoteCancelType(298),
    QuoteEntryID(299),
    QuoteRejectReason(300),
    QuoteResponseLevel(301),
    QuoteSetID(302),
    QuoteRequestType(303),
    TotQuoteEntries(304),
    UnderlyingIDSource(305),
    UnderlyingIssuer(306),
    UnderlyingSecurityDesc(307),
    UnderlyingSecurityExchange(308),
    UnderlyingSecurityID(309),
    UnderlyingSecurityType(310),
    UnderlyingSymbol(311),
    UnderlyingSymbolSfx(312),
    UnderlyingMaturityMonthYear(313),
    UnderlyingMaturityDay(314),
    UnderlyingPutOrCall(315),
    UnderlyingStrikePrice(316),
    UnderlyingOptAttribute(317),
    UnderlyingCurrency(318),
    RatioQty(319),
    SecurityReqID(320),
    SecurityRequestType(321),
    SecurityResponseID(322),
    SecurityResponseType(323),
    SecurityStatusReqID(324),
    UnsolicitedIndicator(325),
    SecurityTradingStatus(326),
    HaltReasonChar(327),
    InViewOfCommon(328),
    DueToRelated(329),
    BuyVolume(330),
    SellVolume(331),
    HighPx(332),
    LowPx(333),
    Adjustment(334),
    TradSesReqID(335),
    TradingSessionID(336),
    ContraTrader(337),
    TradSesMethod(338),
    TradSesMode(339),
    TradSesStatus(340),
    TradSesStartTime(341),
    TradSesOpenTime(342),
    TradSesPreCloseTime(343),
    TradSesCloseTime(344),
    TradSesEndTime(345),
    NumberOfOrders(346),
    MessageEncoding(347),
    EncodedIssuerLen(348),
    EncodedIssuer(349),
    EncodedSecurityDescLen(350),
    EncodedSecurityDesc(351),
    EncodedListExecInstLen(352),
    EncodedListExecInst(353),
    EncodedTextLen(354),
    EncodedText(355),
    EncodedSubjectLen(356),
    EncodedSubject(357),
    EncodedHeadlineLen(358),
    EncodedHeadline(359),
    EncodedAllocTextLen(360),
    EncodedAllocText(361),
    EncodedUnderlyingIssuerLen(362),
    EncodedUnderlyingIssuer(363),
    EncodedUnderlyingSecurityDescLen(364),
    EncodedUnderlyingSecurityDesc(365),
    AllocPrice(366),
    QuoteSetValidUntilTime(367),
    QuoteEntryRejectReason(368),
    LastMsgSeqNumProcessed(369),
    OnBehalfOfSendingTime(370),
    RefTagID(371),
    RefMsgType(372),
    SessionRejectReason(373),
    BidRequestTransType(374),
    ContraBroker(375),
    ComplianceID(376),
    SolicitedFlag(377),
    ExecRestatementReason(378),
    BusinessRejectRefID(379),
    BusinessRejectReason(380),
    GrossTradeAmt(381),
    NoContraBrokers(382),
    MaxMessageSize(383),
    NoMsgTypes(384),
    MsgDirection(385),
    NoTradingSessions(386),
    TotalVolumeTraded(387),
    DiscretionInst(388),
    DiscretionOffset(389),
    BidID(390),
    ClientBidID(391),
    ListName(392),
    TotalNumSecurities(393),
    BidType(394),
    NumTickets(395),
    SideValue1(396),
    SideValue2(397),
    NoBidDescriptors(398),
    BidDescriptorType(399),
    BidDescriptor(400),
    SideValueInd(401),
    LiquidityPctLow(402),
    LiquidityPctHigh(403),
    LiquidityValue(404),
    EFPTrackingError(405),
    FairValue(406),
    OutsideIndexPct(407),
    ValueOfFutures(408),
    LiquidityIndType(409),
    WtAverageLiquidity(410),
    ExchangeForPhysical(411),
    OutMainCntryUIndex(412),
    CrossPercent(413),
    ProgRptReqs(414),
    ProgPeriodInterval(415),
    IncTaxInd(416),
    NumBidders(417),
    TradeType(418),
    BasisPxType(419),
    NoBidComponents(420),
    Country(421),
    TotNoStrikes(422),
    PriceType(423),
    DayOrderQty(424),
    DayCumQty(425),
    DayAvgPx(426),
    GTBookingInst(427),
    NoStrikes(428),
    ListStatusType(429),
    NetGrossInd(430),
    ListOrderStatus(431),
    ExpireDate(432),
    ListExecInstType(433),
    CxlRejResponseTo(434),
    UnderlyingCouponRate(435),
    UnderlyingContractMultiplier(436),
    ContraTradeQty(437),
    ContraTradeTime(438),
    ClearingFirm(439),
    ClearingAccount(440),
    LiquidityNumSecurities(441),
    MultiLegReportingType(442),
    StrikeTime(443),
    ListStatusText(444),
    EncodedListStatusTextLen(445),
    EncodedListStatusText(446);

    private final int code;

    FIX42(int code) {
        this.code = code;
    }

    @Override
    public int code() {
        return code;
    }
}
