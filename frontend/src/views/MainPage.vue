<template>
  <div class="header">
    <div v-if="this.$store.state.userStore.isLogin">
      <div>
        <h4 class="main_title">
          <span>&#128155;&nbsp;</span>'{{ this.$store.state.userStore.userInfo.nickName }}' 님을 위한 추천 공연
        </h4>

        <recommend-list :performance-list="mainUserRecommendList"></recommend-list>
      </div>
    </div>
    <div v-if="this.$store.state.userStore.isLogin">
      <div v-for="(item, index) in actorRecommnedList" :key="index">
        <h4 class="main_title"><span>&#128153;&nbsp;</span>선호 배우 '{{ item.actorName }}' 의 다른 공연</h4>
        <recommend-list :performance-list="item.performanceInfoList"></recommend-list>
      </div>
    </div>
    <div>
      <div v-if="!this.$store.state.userStore.isLogin">
        <h4 class="main_title"><span>&#127915;&nbsp;</span>오늘 뭐보지?</h4>
        <recommend-list :performance-list="mainRecommendList"></recommend-list>
      </div>
      <div>
        <h4 class="main_title"><span>&#127897;&nbsp;</span>뮤지컬 평균 별점 순위</h4>
        <recommend-list :performance-list="musicalList"></recommend-list>
      </div>
    </div>
    <div>
      <div>
        <h4 class="main_title"><span>&#127902;&nbsp;</span>연극 평균 별점 순위</h4>
        <recommend-list :performance-list="playList"></recommend-list>
      </div>
    </div>
  </div>
</template>
<script>
import RecommendList from "@/components/recommend/RecommendList";

import { getMainRecommend, getPerformingRecommend } from "@/api/recommend.js";
import { getRankingShow } from "@/api/show.js";

export default {
  name: "MainPage",
  components: {
    RecommendList,
  },
  data() {
    return {
      musicalList: [],
      playList: [],
      mainUserRecommendList: [],
      actorRecommnedList: [],
      mainRecommendList: [],
    };
  },
  async created() {
    // 평균 별점 순위 리스트 불러오기
    await getRankingShow(
      (response) => {
        this.musicalList = response.data.data.musicalAvgList;
        this.playList = response.data.data.playAvgList;
      },
      (error) => {
        console.log(error);
      }
    );
    // 추천 공연 리스트 불러오기
    await getMainRecommend(
      this.$store.state.userStore.userInfo.userId,
      (response) => {
        this.mainUserRecommendList = response.data.data.recommendListByUser;
        this.actorRecommnedList = response.data.data.recommendListByActor;
      },
      (error) => {
        console.log(error);
      }
    );
    await getPerformingRecommend(
      (response) => {
        this.mainRecommendList = response.data.data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style scoped>
.header {
  margin-left: auto;
  margin-right: auto;
  max-width: 1500px;
}
.main_title {
  font-size: 1.5em;
  margin-left: 0.5em;
  margin-bottom: 0;
}
</style>
