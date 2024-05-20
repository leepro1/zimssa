import { localAxios } from "@/util/http-commons";

const local = localAxios();

const searchByKeyword = async (tradeType, keyword) => {
  try {
    const response = await local.get(`/house/keyword?tradeType=${tradeType}&keyword=${keyword}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching keyword data:", error);
    throw error;
  }
};

const getDetail = async (tradeType, dongName, aptName) => {
  try {
    const response = await local.get(
      `/house/detail?tradeType=${tradeType}&dongName=${dongName}&aptName=${aptName}`
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching detail data:", error);
    return [];
  }
};

export { searchByKeyword, getDetail };
