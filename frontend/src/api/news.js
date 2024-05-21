import { localAxios } from "@/util/http-commons";

const local = localAxios();

const getNewsList = async (keyword) => {
  try {
    const response = await local.get(`/news/${keyword}`);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching keyword data:", error);
    throw error;
  }
};

export { getNewsList };
